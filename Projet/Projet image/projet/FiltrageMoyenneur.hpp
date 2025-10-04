#pragma once 
#include <opencv2/opencv.hpp>
#include <iostream>
#include <vector>

using namespace cv;
using namespace std;

// Fonction de convolution générique 
vector<vector<int>> convolution(const vector<vector<int>>& image, const Mat& kernel) {
    int hauteur = image.size();
    int largeur = image[0].size();
    int kernelSize = kernel.rows;
    int padding = kernelSize / 2;

    vector<vector<int>> imageConvoluee(hauteur, vector<int>(largeur));

    for (int i = padding; i < hauteur - padding; i++) {
        for (int j = padding; j < largeur - padding; j++) {
            float sumBGR[3] = {0, 0, 0};

            for (int m = -padding; m <= padding; m++) {
                for (int n = -padding; n <= padding; n++) {
                    unsigned char r, g, b;
                    intToRgb(image[i + m][j + n], r, g, b);
                    float kernelVal = kernel.at<float>(m + padding, n + padding);
                    
                    sumBGR[0] += b * kernelVal;  
                    sumBGR[1] += g * kernelVal; 
                    sumBGR[2] += r * kernelVal;  
                }
            }
            imageConvoluee[i][j] = rgbToInt(sumBGR[0], sumBGR[1], sumBGR[2]);
        }
    }

    return imageConvoluee;
}

// Fonction pour appliquer un filtre moyenneur
vector<vector<int>> appliquerFiltrageMoyenneur(const vector<vector<int>>& image, int L) {
    Mat kernel = Mat::ones(L, L, CV_32F) / static_cast<float>(L * L);
    return convolution(image, kernel);
}