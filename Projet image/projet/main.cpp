#include <opencv2/opencv.hpp>
#include <iostream>

#include <vector>
#include <algorithm>
#include <filesystem>
#include <cmath>
#include <fstream>
#include "fonction.hpp"
#include "filtreMedian.hpp"
#include "FiltresLaplacien.hpp"
#include "FiltreSobel.hpp"
#include "convolutionGaussian.hpp"
#include "FiltrageMoyenneur.hpp"
#include "Histogramme.hpp"
#include "histogrammeCumule.hpp"
#include "egaliseur.hpp"
#include "Lut.hpp"
#include "Etirement.hpp"
#include "Bonus.hpp"


using namespace cv;
using namespace std;
namespace fs = std::filesystem;

// Fonction pour lire les noyaux  depuis un fichier 
std::vector<std::vector<std::vector<double>>> lireNoyaux(const std::string &filename) {
    std::ifstream infile(filename);
    std::vector<std::vector<std::vector<double>>> matrices;

    if (!infile.is_open()) {
        std::cerr << "Impossible d'ouvrir le fichier : " << filename << std::endl;
        return matrices; 
    }

    while (true) {
        int rows, cols;
        if (!(infile >> rows >> cols)) {
            break;
        }

        std::vector<std::vector<double>> matrix(rows, std::vector<double>(cols, 0.0));
        
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (!(infile >> matrix[i][j])) {
                    std::cerr << "Erreur lors de la lecture des données de la matrice." << std::endl;
                    return matrices; 
                }
            }
        }

        matrices.push_back(matrix);
    }

    return matrices;
}


int main() {
   
    string dossierImages =  "Images/Images_Original";  
    bool arret = false;
    while(!arret) {

        vector<string> images;
        cout << "Liste des images dans le dossier : " << dossierImages << endl;
        int index = 1;
        for (const auto& entry : fs::directory_iterator(dossierImages)) {
            if (entry.is_regular_file() && (entry.path().extension() == ".jpg" || entry.path().extension() == ".png")) {
                images.push_back(entry.path().string());
                cout << index++ << ". " << entry.path().filename() << endl;
            }
        }

        int choix = 0;
        cout << "Entrez le numéro de l'image que vous voulez traiter : ";
        cin >> choix;
        if (choix < 1 || choix > images.size()) {
            cout << "Choix invalide. Veuillez entrer un numéro valide." << endl;
            return -1;
        }

        string imageChoisie = images[choix - 1];
        Mat image = imread(imageChoisie, IMREAD_COLOR);
        
        if (image.empty()) {
            cout << "Erreur lors du chargement de l'image." << endl;
            return -1;
        }

        imshow("Image Choisie", image);
        waitKey(0);

        cout << "\nChoisissez :\n";
        cout << "1. Les Filtres et transformations géométriques  \n";
        cout << "2. La partie Histogramme\n";
        cout << "Entrez votre choix (1-2): ";
        int choixpartie;
        cin >> choixpartie;

        if(choixpartie == 1){
            cout << "\nChoisissez le filtre à appliquer :\n";
            cout << "1. Filtre Médian\n";
            cout << "2. Filtre Moyenneur\n";
            cout << "3. Filtre Gaussien\n";
            cout << "4. Filtre Laplacien\n";
            cout << "5. Filtre Sobel\n";
            cout << "6. Redimensionner Image\n";
            cout << "7. Zoom Image\n";
            cout << "Entrez votre choix (1-7): ";
            
            int choixFiltre;
            cin >> choixFiltre;

            vector<vector<int>> imageMatrix = convertImageVector(image);
            vector<vector<int>> imageFiltrée; 
            Mat imageFiltréeFinale; 
            double sigma;
            int taille;

            int tailleNoyau = 3;
            vector<vector<vector<double>>> noyaux;
            switch (choixFiltre) {
                case 1:
                        cout << "Entrez la taille du noyau pour le filtre médian (impair, par exemple 3, 5, 7) : ";
                        cin >> tailleNoyau;
                        if (tailleNoyau % 2 == 0) {
                            cout << "La taille du noyau doit être un nombre impair. Utilisation de 3." << endl;
                            tailleNoyau = 3;  
                        }
                        imageFiltrée = appliquerFiltreMedian(imageMatrix, tailleNoyau); 
                        imageFiltréeFinale = convertImageMat(imageFiltrée); 
                        cout << "Filtre médian appliqué." << endl;
                        break;
                case 2:
                        cout << "Entrez la taille du noyau pour le filtre moyenneur (impair, par exemple 3, 5, 7) : ";
                        cin >> tailleNoyau;
                        if (tailleNoyau % 2 == 0) {
                            cout << "La taille du noyau doit être un nombre impair. Utilisation de 3." << endl;
                            tailleNoyau = 3;  
                        }
                        imageFiltrée =appliquerFiltrageMoyenneur(imageMatrix, tailleNoyau); 
                        imageFiltréeFinale = convertImageMat(imageFiltrée); 
                        cout << "Filtre moyenneur appliqué." << endl;
                        break;
                case 3:
                        cout << "\nChoisissez une option pour le noyau gaussien :\n";
                        cout << "1. Choisir un noyau à partir d'un fichier\n";
                        cout << "2. Générer un noyau basé sur un sigma\n";
                        cout << "Entrez votre choix (1 ou 2): ";
                        
                        int choix;
                        cin >> choix;
                        
                        if (choix == 1) {

                            noyaux = lireNoyaux("noyauxGaussien.txt");
                            cout << "\nChoisissez un noyau gaussien :\n";
                            for (int i = 0; i < noyaux.size(); ++i) {
                                cout << i + 1 << ". Noyau " << noyaux[i].size() << "x" << noyaux[i][0].size() << endl;
                            }
                            cout << "Entrez votre choix (1-" << noyaux.size() << "): ";
                            
                            int choixNoyau;
                            cin >> choixNoyau;

                            if (choixNoyau < 1 || choixNoyau > noyaux.size()) {
                                cout << "Choix invalide. Veuillez entrer un numéro valide." << endl;
                                return -1;
                            }
                            imageFiltrée = applyGaussianFilter(imageMatrix, noyaux[choixNoyau - 1]);
                            imageFiltréeFinale = convertImageMat(imageFiltrée); 

                            cout << "Filtre gaussien appliqué." << endl;
                        }else{
                                        
                                
                                cout << "Entrez la valeur de sigma : ";
                                cin >> sigma;
                                cout << "Entrez la taille du noyau (un nombre impair) : ";
                                cin >> taille;
                                imageFiltrée =applyGaussianFilterSigma(imageMatrix,taille, sigma); 
                                imageFiltréeFinale = convertImageMat(imageFiltrée); 
                
                        }
                        break;
                    case 4:
                        noyaux = lireNoyaux("noyauxLaplacien.txt");
                        cout << "\nChoisissez un noyau Laplacien :\n";
                        for (int i = 0; i < noyaux.size(); ++i) {
                            cout << i + 1 << ". Noyau " << noyaux[i].size() << "x" << noyaux[i][0].size() << endl;
                        }
                        cout << "Entrez votre choix (1-" << noyaux.size() << "): ";
                        
                        int choixNoyau;
                        cin >> choixNoyau;

                        if (choixNoyau < 1 || choixNoyau > noyaux.size()) {
                            cout << "Choix invalide. Veuillez entrer un numéro valide." << endl;
                            return -1;
                        }
                        imageFiltrée = appliquerLaplacien(imageMatrix, noyaux[choixNoyau - 1]);
                        imageFiltréeFinale = convertImageMat(imageFiltrée);
                        cout << "Filtre Laplacien appliqué." << endl;
                        break;
                case 5:
                        noyaux = lireNoyaux("noyauxSobelX.txt");
                        cout << "\nChoisissez un noyau SobelX :\n";
                        for (int i = 0; i < noyaux.size(); ++i) {
                            cout << i + 1 << ". Noyau " << noyaux[i].size() << "x" << noyaux[i][0].size() << endl;
                        }
                        cout << "Entrez votre choix (1-" << noyaux.size() << "): ";
                        int choixNoyauX;
                        cin >> choixNoyauX;
                        if (choixNoyauX < 1 || choixNoyauX > noyaux.size()) {
                            cout << "Choix invalide. Veuillez entrer un numéro valide." << endl;
                            return -1;
                        }
                        noyaux = lireNoyaux("noyauxSobelY.txt");
                        cout << "\nChoisissez un noyau SobelY :\n";
                        for (int i = 0; i < noyaux.size(); ++i) {
                            cout << i + 1 << ". Noyau " << noyaux[i].size() << "x" << noyaux[i][0].size() << endl;
                        }
                        cout << "Entrez votre choix (1-" << noyaux.size() << "): ";
                        
                        int choixNoyauY;
                        cin >> choixNoyauY;

                        if (choixNoyauY < 1 || choixNoyauY > noyaux.size()) {
                            cout << "Choix invalide. Veuillez entrer un numéro valide." << endl;
                            return -1;
                        }
                        imageFiltrée =appliquerSobel(imageMatrix, noyaux[choixNoyauX - 1], noyaux[choixNoyauY - 1]);
                        imageFiltréeFinale = convertImageMat(imageFiltrée); 
                        cout << "Filtre Sobel appliqué." << endl;
                        break;
                case 6 :{
                        int newWidth, newHeight;
                        cout << "\nEntrez la nouvelle largeur (largeur) : ";
                        cin >> newWidth;
                        cout << "Entrez la nouvelle hauteur (longueur) : ";
                        cin >> newHeight;
                        if (newWidth <= 0 || newHeight <= 0) {
                            cout << "Les dimensions doivent être positives !" << endl;
                            return -1;
                        }
                        vector<vector<int>> imageRedim = redimensionnerImage(imageMatrix, newWidth, newHeight);
                        imageFiltréeFinale = convertImageMat(imageRedim);
                        break;
                }
                case 7 :{
                        int zoom;
                        cout << "\nEntrez le Zoom : ";
                        cin >> zoom;
                        if (zoom <=1) {
                            cout << "erreur" << endl;
                            return -1;
                        }
                        vector<vector<int>> imageRedim = zoomImage(imageMatrix, zoom);
                        imageFiltréeFinale = convertImageMat(imageRedim);
                        break;
                }
                default:
                    cout << "Choix invalide." << endl;
                    return -1;
            }

            imshow("Image Après Filtrage", imageFiltréeFinale);
            waitKey(0);  

            imwrite("resultatConsole/image_filtree_" + to_string(choixFiltre) + ".jpg", imageFiltréeFinale);
        }else {

            cout << "Choisissez l'operation que vous souhaitez effectuer:" << endl;
            cout << "1. Calcul d'histogramme" << endl;
            cout << "2. Calcul d'histogramme cumule" << endl;
            cout << "3. Egalisation d'histogramme" << endl;
            cout << "4. Etirement d'histogramme" << endl;
            cout << "Entrez votre choix (1-4): ";

            int choix;
            cin >> choix;

            switch (choix) {
                case 1: {
                        vector<int> histogramme(256, 0);
                        calculerHistogrammeGrayscale(image, histogramme);
                        Mat his=afficherHistogramme(histogramme);
                        cout << "Histogramme calcule: " << endl;
                        cout << "[ ";
                        for (int i = 0; i < 256; i++) {
                            cout << histogramme[i] << " ";
                        }
                        cout << "]" << endl;
                        imwrite("resultatConsole/histogramme.png", his);
                        break;
                }
                case 2: {
                    
                        vector<int> histogramme(256, 0);
                        calculerHistogrammeGrayscale(image, histogramme);
                        vector<int> histogrammeCumule(256, 0);
                        calculerHistogrammeCumule(histogramme, histogrammeCumule);
                        Mat hisc=afficherHistogrammeCumule(histogrammeCumule);
                        cout << "Histogramme cumule calcule: " << endl;
                        cout << "[ ";
                        for (int i = 0; i < 256; i++) {
                            cout << histogrammeCumule[i] << " ";
                        }
                        cout << "]" << endl;
                        imwrite("resultatConsole/histogrammeCumele.png", hisc);
                        break;
                }
                case 3: {
                        vector<vector<int>> imageMatrix = convertImageVector(image);
                        Mat imageEqualized = egaliserHistogramme(imageMatrix);
                        imshow("Image Egalisee", imageEqualized);
                        imwrite("resultatConsole/image_egalisee.jpg", imageEqualized);
                        cout << "Egalisation d'histogramme terminee." << endl;
                        waitKey(0);
                        break;
                }
                case 4: {
                        Mat imageGrayscale;
                        cvtColor(image, imageGrayscale, COLOR_BGR2GRAY);
                        Mat imageEtendue = etirementHistogramme(imageGrayscale);
                        imshow("Image Etendue", imageEtendue);
                        imwrite("resultatConsole/image_etendue.jpg", imageEtendue);
                        cout << "Etirement d'histogramme termine." << endl;
                        waitKey(0);
                        break;
                }
                default:
                    cout << "Choix invalide." << endl;
                    break;
            }
        }

        string reponse;
        cout << "Voulez-vous continuer à tester d'autres images ?" << endl;
        cout << "OUI              NON" << endl;
        cin >> reponse;
        for (auto &c : reponse) {
            c = toupper(c);
        }
        if (reponse == "NON") 
                 arret=true;
    }

    return 0;
}

