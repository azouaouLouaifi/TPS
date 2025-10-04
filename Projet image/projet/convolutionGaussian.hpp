#pragma once
#include <iostream>
#include <vector>
#include <algorithm>
#include <filesystem>
#include <cmath>
#include "fonction.hpp"
using namespace cv;
using namespace std;

// Création du noyau Gaussien
vector<vector<double>> createGaussianKernel(int size, double sigma) {
    vector<vector<double>> kernel(size, vector<double>(size));
    double sum = 0.0;
    int center = size / 2;
    for (int x = -center; x <= center; x++) {
        for (int y = -center; y <= center; y++) {
            kernel[x + center][y + center] = 
                exp(-(x*x + y*y)/(2*sigma*sigma)) / (2*M_PI*sigma*sigma);
            sum += kernel[x + center][y + center];
        }
    }

    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            kernel[i][j] /= sum;
        }
    }

    return kernel;
}

// fonction pour l'Application du filtre Gaussien avec sigma 
vector<vector<int>> applyGaussianFilterSigma(const vector<vector<int>>& imageMatrix, int size, double sigma) {
    vector<vector<double>> kernel = createGaussianKernel(size, sigma);
    int rows = (int)imageMatrix.size();
    int cols = (int)imageMatrix[0].size();
    vector<vector<int>> result = imageMatrix;
    int center = size / 2;
    for (int i = center; i < rows - center; i++) {
        for (int j = center; j < cols - center; j++) {
            double sumB = 0.0, sumG = 0.0, sumR = 0.0;
            for (int k = -center; k <= center; k++) {
                for (int l = -center; l <= center; l++) {
                    unsigned char r, g, b;
                    intToRgb(imageMatrix[i + k][j + l], r, g, b);
                    double weight = kernel[k + center][l + center];
                    sumB += b * weight;
                    sumG += g * weight;
                    sumR += r * weight;
                }
            }
            unsigned char newB = cv::saturate_cast<unsigned char>(sumB);
            unsigned char newG = cv::saturate_cast<unsigned char>(sumG);
            unsigned char newR = cv::saturate_cast<unsigned char>(sumR);
            result[i][j] = rgbToInt(newB, newG, newR);
        }
    }
    return result;
}


// Fonction Application du filtre Gaussien avec noyau
vector<vector<int>> applyGaussianFilter(const vector<vector<int>>& imageMatrix, vector<vector<double>> kernel) {
    int rows = (int)imageMatrix.size();
    int cols = (int)imageMatrix[0].size();
    vector<vector<int>> result = imageMatrix;
    int center = kernel.size() / 2;
    for (int i = center; i < rows - center; i++) {
        for (int j = center; j < cols - center; j++) {
            double sumB = 0.0, sumG = 0.0, sumR = 0.0;
            for (int k = -center; k <= center; k++) {
                for (int l = -center; l <= center; l++) {
                    unsigned char r, g, b;
                    intToRgb(imageMatrix[i + k][j + l], r, g, b);
                    double weight = kernel[k + center][l + center];
                    sumB += b * weight;
                    sumG += g * weight;
                    sumR += r * weight;
                }
            }

            unsigned char newB = cv::saturate_cast<unsigned char>(sumB);
            unsigned char newG = cv::saturate_cast<unsigned char>(sumG);
            unsigned char newR = cv::saturate_cast<unsigned char>(sumR);
            result[i][j] = rgbToInt(newB, newG, newR);
        }
    }

    return result;
}
