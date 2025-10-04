#pragma once 
#include "Histogramme.hpp"
#include "histogrammecumule.hpp"
#include "Lut.hpp"

// Fonction pour appliquer l'etirement de l'histogramme
Mat etirementHistogramme(const Mat& image) {
    vector<int> histogramme(256, 0);
    calculerHistogrammeGrayscale(image, histogramme);  

    vector<int> histogrammeCumule(256, 0);
    calculerHistogrammeCumule(histogramme, histogrammeCumule);  

    int totalPixels = image.rows * image.cols;  

    vector<int> LUT(256, 0);
    creerLUT(histogrammeCumule, LUT, totalPixels); 

    Mat imageEtendue = image.clone();
    for (int i = 0; i < image.rows; ++i) {
        for (int j = 0; j < image.cols; ++j) {
            int pixelValue = image.at<uchar>(i, j);
            imageEtendue.at<uchar>(i, j) = LUT[pixelValue];
        }
    }

    return imageEtendue;
}