#pragma once
#include <opencv2/opencv.hpp>
#include <iostream>
#include <vector>
#include <algorithm>
using namespace cv;
using namespace std;

// fonction pour appliquer le filtre laplacien
vector<vector<int>> appliquerLaplacien(const vector<vector<int>>& image, const vector<vector<double>>& kernel) {
    int hauteur = (int)image.size();
    int largeur = (int)image[0].size();
    int kernelSize = (int)kernel.size();
    int padding = kernelSize / 2;

    vector<vector<int>> imageConvoluee(hauteur, vector<int>(largeur));

    for (int i = padding; i < hauteur - padding; i++) {
        for (int j = padding; j < largeur - padding; j++) {
            vector<float> sumBGR = {0.0f, 0.0f, 0.0f};
            
            // Appliquer le noyau
            for (int m = -padding; m <= padding; m++) {
                for (int n = -padding; n <= padding; n++) {
                    unsigned char r, g, b;
                    intToRgb(image[i + m][j + n], r, g, b);
                    double kernelVal = kernel[m + padding][n + padding];
                    
                    sumBGR[0] += b * (float)kernelVal;
                    sumBGR[1] += g * (float)kernelVal;
                    sumBGR[2] += r * (float)kernelVal;
                }
            }

            // Calculer l'intensité du contour
            float intensite = (fabs(sumBGR[0]) + fabs(sumBGR[1]) + fabs(sumBGR[2])) / 3.0f;
            
            // Déterminer la nouvelle valeur du pixel
            unsigned char newB, newG, newR;
            if (intensite > 30.0f) {
                // Normalisation rudimentaire entre 0 et 255
                float normalizedIntensity = std::min(255.0f, (intensite / 255.0f) * 255.0f);
                newB = newG = newR = (unsigned char)normalizedIntensity;
            } else {
                newB = newG = newR = 0;
            }

            imageConvoluee[i][j] = rgbToInt(newB, newG, newR);
        }
    }

    return imageConvoluee;
}
