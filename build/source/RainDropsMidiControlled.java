import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import de.looksgood.ani.*; 
import themidibus.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class RainDropsMidiControlled extends PApplet {

/**
 * shows how to create a sequence of animations (timeline)
 *
 * KEYS
 * space           : toggle, pause and resume sequence
 * s               : start or restart sequence
 */




MidiBus bus;
ArrayList<Point> points = new ArrayList<Point>();
/* OPTIONS */
boolean opacityMode = false; // est-ce que l'opacit\u00e9 diminue au cours de l'apparition ?
boolean disappearMode = true; // est-ce que l'objet disparait au cours du temps ?
String drawMode = "STROKE"; // mode de dessin, \u201cSTROKE" ou "FILL"

int opac = 255;
int canalRed, canalGreen, canalBlue = 125;
int shapeColor;
int shapeOpacity = 125;
float shapeStroke = 4;

public void setup() {
  //size(1440,900, P2D);
  
  
  noStroke();
  textAlign(CENTER);
  background(0);

  bus.list();
  bus = new MidiBus(this, 3, 4);

}

public void draw() {
  background(0);
  //println(canalRed, canalGreen, canalBlue, shapeOpacity, shapeStroke);
  shapeColor = color(canalRed, canalGreen, canalBlue);

  for(int i = 0 ; i < points.size() ; i++)
  {
    Point part = points.get(i);
    part.display();
  }
}

public void controllerChange(int channel, int number, int value){
  if(number == 81){
    canalRed = floor(map(value, 0, 127, 0, 255));
  }
  if(number == 82){
    canalGreen = floor(map(value, 0, 127, 0, 255));
  }
  if(number == 83){
    canalBlue = floor(map(value, 0, 127, 0, 255));
  }
  if(number == 84){
    shapeOpacity = floor(map(value, 0, 127, 0, 255));
  }
  if(number == 85){
    shapeStroke = floor(map(value, 0, 127, 0.5f, 120));
  }
}
public void noteOn(int channel, int pitch, int velocity) {
    //int newVal = 228;
    //Ani.to(this, 1.0, "x: "+newVal+", y:"+newVal+", diameter:56, diameter:56, opac:0", Ani.QUINT_IN_OUT);
    println("received");

    points.add(new Point(random(width), random(height), random(0.1f), map(velocity, 0, 127, 1000, 0), shapeColor, shapeOpacity, shapeStroke));
}
class Point {
   int px, py; //position
   int sOpac;
   float ssize, dsize, opac, easing, tsize, sStroke; // taille de d\u00e9part, diff (dynamique) entre taille finale et taille courante, opacit\u00e9 courante, easing, taille finale
   int sColor;
   float opacDisp = 0;
   float dispSpeed = 1;

    Point (float pointX, float pointY, float Easing, float TSize, int shapeColor, int shapeOpacity, float shapeStroke){
        px = floor(pointX);
        py = floor(pointY);
        easing = Easing;
        ssize = 0;
        tsize = TSize;
        sColor = shapeColor;
        sOpac = shapeOpacity;
        sStroke = shapeStroke;
    }
    public void display(){
        dsize = tsize - ssize; // update dsize
        ssize += dsize * easing; // update size courante

        //controle du mode d'opacit\u00e9
        if(opacityMode == true){
          opac = (255*tsize/ssize)/10;
        }
        else{
          opac = sOpac;
        }

        //Extinction de l'objet
        if(disappearMode == true){
          if(ssize >= tsize-10){
            opacDisp += dispSpeed;
            if(opacDisp > 255){
              opacDisp = 255;
            }
          }
        }

        // controle du mode de dessin
        if(drawMode == "FILL"){
          fill(sColor, opac-opacDisp);
          noStroke();
        }
        if(drawMode == "STROKE"){
          stroke(sColor, opac-opacDisp);
          strokeWeight(sStroke);
          noFill();
        }

        // Augmentation de la taille, jusqu'\u00e0 la taille finale
        if(ssize < tsize){
           ellipse(px, py, ssize, ssize);
        }
        else{
            ellipse(px, py, tsize, tsize);
        }
    }
}
  public void settings() {  fullScreen();  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "RainDropsMidiControlled" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
