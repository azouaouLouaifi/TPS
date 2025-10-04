#pragma once 
#include <vector>
#include <cmath>  
#include <iostream>
using namespace std;


// Fonction pour redimensionner une image 
vector<vector<int>> redimensionnerImage(const vector<vector<int>>& imageOriginale, int largeur, int hauteur) {
    int originalHeight = (int)imageOriginale.size();
    if (originalHeight == 0) {
        return {};
    }
    int originalWidth = (int)imageOriginale[0].size();

    vector<vector<int>> imageRedim(hauteur, vector<int>(largeur));

    for (int y = 0; y < hauteur; y++) {
        for (int x = 0; x < largeur; x++) {
            // Trouver la position correspondante dans l'image originale
            double x_ratio = (double)x / (double)largeur;
            double y_ratio = (double)y / (double)hauteur;

            int x_original = (int)round(x_ratio * (originalWidth - 1));
            int y_original = (int)round(y_ratio * (originalHeight - 1));

            imageRedim[y][x] = imageOriginale[y_original][x_original];
        }
    }
    return imageRedim;
}

// Fonction pour faire un zoom sur une image  
vector<vector<int>> zoomImage(const vector<vector<int>>& imageOriginale, double factor) {
    int originalHeight = (int)imageOriginale.size();
    if (originalHeight == 0) return {};
    int originalWidth = (int)imageOriginale[0].size();

    int newWidth = (int)(originalWidth * factor);
    int newHeight = (int)(originalHeight * factor);

    return redimensionnerImage(imageOriginale, newWidth, newHeight);
}
