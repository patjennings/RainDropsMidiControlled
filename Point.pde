class Point {
   int px, py; //position
   int sOpac;
   float ssize, dsize, opac, easing, tsize, sStroke; // taille de départ, diff (dynamique) entre taille finale et taille courante, opacité courante, easing, taille finale
   color sColor;
   float opacDisp = 0;
   float dispSpeed = 1;

    Point (float pointX, float pointY, float Easing, float TSize, color shapeColor, color shapeOpacity, float shapeStroke){
        px = floor(pointX);
        py = floor(pointY);
        easing = Easing;
        ssize = 0;
        tsize = TSize;
        sColor = shapeColor;
        sOpac = shapeOpacity;
        sStroke = shapeStroke;
    }
    void display(){
        dsize = tsize - ssize; // update dsize
        ssize += dsize * easing; // update size courante

        //controle du mode d'opacité
        if(opacityMode == true){
          opac = (255*tsize/ssize)/10;
        }
        else{
          opac = sOpac;
        }

        //Extinction de l'objet
        if(ssize >= tsize-10){
          opacDisp += dispSpeed;
          if(opacDisp > 255){
            opacDisp = 255;
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

        // Augmentation de la taille, jusqu'à la taille finale
        if(ssize < tsize){
           ellipse(px, py, ssize, ssize);
        }
        else{
            ellipse(px, py, tsize, tsize);
        }
    }
}
