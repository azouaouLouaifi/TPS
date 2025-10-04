#pragma once
#include <opencv2/opencv.hpp>
#include <iostream>
#include <vector>
#include <algorithm>
#include <filesystem>

using namespace cv;
using namespace std;

// Fonction pour appliquer le filtre de Sobel
vector<vector<int>> appliquerSobel(const vector<vector<int>>& image, 
                                   const vector<vector<double>>& kernelX, 
                                   const vector<vector<double>>& kernelY) 
{
    int hauteur = (int)image.size();
    int largeur = (int)image[0].size();
    int kernelSize = (int)kernelX.size();
    int padding = kernelSize / 2;

    vector<vector<int>> imageConvoluee(hauteur, vector<int>(largeur));

    for (int i = padding; i < hauteur - padding; i++) {
        for (int j = padding; j < largeur - padding; j++) {
            vector<float> sumBGR_X = {0.0f, 0.0f, 0.0f};
            vector<float> sumBGR_Y = {0.0f, 0.0f, 0.0f};
            
            for (int m = -padding; m <= padding; m++) {
                for (int n = -padding; n <= padding; n++) {
                    unsigned char r, g, b;
                    intToRgb(image[i + m][j + n], r, g, b);
                    
                    double kernelValX = kernelX[m + padding][n + padding];
                    double kernelValY = kernelY[m + padding][n + padding];
                    
                    sumBGR_X[0] += (float)(b * kernelValX);
                    sumBGR_X[1] += (float)(g * kernelValX);
                    sumBGR_X[2] += (float)(r * kernelValX);
                    sumBGR_Y[0] += (float)(b * kernelValY);
                    sumBGR_Y[1] += (float)(g * kernelValY);
                    sumBGR_Y[2] += (float)(r * kernelValY);
                }
            }

            float magnitudeB = std::sqrt(sumBGR_X[0]*sumBGR_X[0] + sumBGR_Y[0]*sumBGR_Y[0]);
            float magnitudeG = std::sqrt(sumBGR_X[1]*sumBGR_X[1] + sumBGR_Y[1]*sumBGR_Y[1]);
            float magnitudeR = std::sqrt(sumBGR_X[2]*sumBGR_X[2] + sumBGR_Y[2]*sumBGR_Y[2]);

            float intensite = (magnitudeB + magnitudeG + magnitudeR) / 3.0f;

            // Normaliser et seuiller
            unsigned char newB, newG, newR;
            if (intensite > 30.0f) {
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
