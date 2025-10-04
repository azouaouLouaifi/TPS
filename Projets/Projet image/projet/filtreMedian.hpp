#pragma once 
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

// Fonction pour appliquer le filtre médian
vector<vector<int>> appliquerFiltreMedian(const vector<vector<int>>& image, int tailleFenetre) {
    int hauteur = image.size();
    int largeur = image[0].size();
    vector<vector<int>> imageFiltrée = image;

    for (int i = 0; i < hauteur; i++) {
        for (int j = 0; j < largeur; j++) {
            vector<unsigned char> voinsinsB, voinsinsG, voinsinsR;

            for (int di = -tailleFenetre/2; di <= tailleFenetre/2; di++) {
                for (int dj = -tailleFenetre/2; dj <= tailleFenetre/2; dj++) {
                    int ni = i + di;
                    int nj = j + dj;

                    if (ni >= 0 && ni < hauteur && nj >= 0 && nj < largeur) {
                        unsigned char r, g, b;
                        intToRgb(image[ni][nj], r, g, b);
                        voinsinsB.push_back(b);
                        voinsinsG.push_back(g);
                        voinsinsR.push_back(r);
                    }
                }
            }

            sort(voinsinsB.begin(), voinsinsB.end());
            sort(voinsinsG.begin(), voinsinsG.end());
            sort(voinsinsR.begin(), voinsinsR.end());

            int medB = voinsinsB[voinsinsB.size() / 2];
            int medG = voinsinsG[voinsinsG.size() / 2];
            int medR = voinsinsR[voinsinsR.size() / 2];

            imageFiltrée[i][j] = rgbToInt(medB, medG, medR);
        }
    }
    return imageFiltrée;
}
