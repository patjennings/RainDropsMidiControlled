/**
 * shows how to create a sequence of animations (timeline)
 *
 * KEYS
 * space           : toggle, pause and resume sequence
 * s               : start or restart sequence
 */

import de.looksgood.ani.*;
import themidibus.*;

MidiBus bus;
ArrayList<Point> points = new ArrayList<Point>();
/* OPTIONS */
boolean opacityMode = false; // est-ce que l'opacité diminue au cours de l'apparition ?
boolean disappearMode = false; // est-ce que l'objet disparait au cours du temps ?
String drawMode = "STROKE"; // mode de dessin, “STROKE" ou "FILL"

int opac = 255;
int canalRed, canalGreen, canalBlue = 125;
color shapeColor;
int shapeOpacity = 125;
float shapeStroke = 4;

void setup() {
  //size(1440,900, P2D);
  fullScreen();
  smooth();
  noStroke();
  textAlign(CENTER);
  background(0);

  bus.list();
  bus = new MidiBus(this, 3, 4);

}

void draw() {
  background(0);
  //println(canalRed, canalGreen, canalBlue, shapeOpacity, shapeStroke);
  shapeColor = color(canalRed, canalGreen, canalBlue);

  for(int i = 0 ; i < points.size() ; i++)
  {
    Point part = points.get(i);
    part.display();
  }
}

void controllerChange(int channel, int number, int value){
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
    shapeStroke = floor(map(value, 0, 127, 0.5, 120));
  }
}
void noteOn(int channel, int pitch, int velocity) {
    //int newVal = 228;
    //Ani.to(this, 1.0, "x: "+newVal+", y:"+newVal+", diameter:56, diameter:56, opac:0", Ani.QUINT_IN_OUT);
    println("received");

    points.add(new Point(random(width), random(height), random(0.1), map(velocity, 0, 127, 1000, 0), shapeColor, shapeOpacity, shapeStroke));
}
