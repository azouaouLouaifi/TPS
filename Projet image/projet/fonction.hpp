#pragma once
#include <opencv2/opencv.hpp>
#include <iostream>
#include <vector>
#include <algorithm>
#include <filesystem>
#include <cmath>
using namespace std;
using namespace cv;


// Fonction qui convertit les composantes BGR en une seule valeur
unsigned int rgbToInt(unsigned char b, unsigned char g, unsigned char r) {
    return (r << 16) | (g << 8) | b;
}

// Fonction qui convertit la valeur en composantes BGR  

void intToRgb(unsigned int color, unsigned char &r, unsigned char &g, unsigned char &b) {
    r = (color >> 16) & 0xFF;
    g = (color >> 8) & 0xFF;
    b = color & 0xFF;
}

// Convertir l'image en matrice 
vector<vector<int>> convertImageVector(const Mat& image) {
    vector<vector<int>> imageMatrix(image.rows, vector<int>(image.cols));
    for(int i = 0; i < image.rows; i++) {
        for(int j = 0; j < image.cols; j++) {
            Vec3b pixel = image.at<Vec3b>(i, j);
            imageMatrix[i][j] = rgbToInt(pixel[0], pixel[1], pixel[2]);
        }
    }
    return imageMatrix;
}

// Convertir la matrice en image
Mat convertImageMat(const vector<vector<int>>& imageMatrix) {
    int rows = imageMatrix.size();
    int cols = imageMatrix[0].size();
    Mat image(rows, cols, CV_8UC3);
    
    for(int i = 0; i < rows; i++) {
        for(int j = 0; j < cols; j++) {
            unsigned char r, g, b;
            intToRgb(imageMatrix[i][j], r, g, b);
            image.at<Vec3b>(i, j) = Vec3b(b, g, r);
        }
    }
    return image;
}