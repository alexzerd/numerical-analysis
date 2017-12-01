#include <iomanip>
#include <cmath>
#include <iostream>
#include <conio.h>
using namespace std;

typedef double(*Pfunc)(double*);
typedef void(*PJac)(double*, double**&, Pfunc*, int);
const double eps1 = 1e-9, eps2 = 1e-9, NIT = 100;


double func1_1(double *X){
	return log(1 + (X[0] + X[1]) / 5 - sin(X[1] / 3) - X[0] + 1.1);
}

double func1_2(double *X){
	return cos(X[0] * X[1] / 6) - X[1] + 0.5;
}

double func3_1(double *x){
	return x[0] + x[1] - 3;
}
double func3_2(double *x){
	return x[0] + 2*x[1]*x[1] - 9;
}

void Jacobi(double *x, double **&J, Pfunc *Func, int dim){
	int i = 0;
	double *X = new double[dim];
	for (i = 0; i < dim; i++){
		X[i] = x[i];
	}

	for (int j = 0; j < dim; j++){
		if (j != 0)
		{
			X[j - 1] = x[j - 1];
		}
		X[j] = x[j] + eps1;
		for (int i = 0; i < dim; i++){
			J[i][j] = (Func[i](X) - Func[i](x)) / (eps1);
		}
	}
}

double func2_1(double *x){
	return x[0] - x[1] - 6 * log10(x[0]) - 1;
}
double func2_2(double *x){
	return x[0] - 3*x[1] - 6 * log10(x[1]) - 2;
}
void vecF(double *&F, double *x, Pfunc *Func, int dim){
	for (int i = 0; i < dim; i++){
		F[i] = -1 * Func[i](x);
	}
}

void copyArrays(double *A, double *B, int dim){
	for (int i = 0; i < dim; i++)
	{
		A[i] = B[i];
	}
}

void head_of_table(){
	cout << setw(4) << "k" << setw(10) << "d1" << setw(10) << "d2" << endl;
}

void output_line(int k, double d1, double d2){
	cout << setw(4) << k << setw(14) << d1 << setw(14) << d2 << endl;
}

void getMemory(double **&arr, int dim){
	arr = new double*[dim];
	for (int i = 0; i < dim; i++){
		arr[i] = new double[dim];
	}
}

void getMemoryforArr(double **&arr, int dim){
	arr = new double*[dim];
	for (int i = 0; i <= dim; i++){
		arr[i] = new double[dim];
	}
}

void freeMemory(double **&arr, int dim)
{
	for (int i = 0; i < dim; i++)
		delete[] arr[i];
	delete[] arr;
}

double delta1(double *x, Pfunc *Func){
	if (abs(Func[0](x)) >= abs(Func[1](x)))
	{
		return abs(Func[0](x));
	}
	else
	{
		return abs(Func[1](x));
	}
}

double delta2(double *x, double *xprev, int dim, int i){
	double delta = 1;
	if (abs(x[0]) < 1){
		delta = abs(x[0] - xprev[0]);
	}
	else{
		delta = abs((x[0] - xprev[0])/x[0]);
	}
	{
		if (abs(x[i])<1 && abs(x[i] - xprev[i]) > delta)
			delta = abs(x[i] - xprev[i]);
		else if (delta < abs((x[i] - xprev[i]) / x[i]))
			delta = abs((x[i] - xprev[i]) / x[i]);
	}
	return delta;
}

void concat(double **&arr, double **A, double *B, int dim){
	int i, j;
	for (i = 0; i < dim; i++){
		for (j = 0; j < dim; j++){
			arr[i][j] = A[i][j];
		}
		arr[i][j] = B[i];
	}
}

int Newton_method(){
	//Pfunc Func[] = { func2_1, func2_2};
	//PJac Jac = Jacobi1;
	//double X[] = { 0.5, 0.2 };
	//int dim = 2;

	//Pfunc Func[] = { func1_1, func1_2 };
	//PJac Jac = Jacobi;
	//double X[] = { 1.0, 1.0 };
	//int dim = 2;

	Pfunc Func[] = { func3_1, func3_2 };
	PJac Jac = Jacobi;
	double X[] = { 100.0, 100.0 };
	int dim = 2;

	int k = 1;
	head_of_table();
	double d1, d2;
	double *F;
	double **J;
	double **arr;
	for (k = 0; k < NIT; k++){
		F = new double[dim];
		double *dx = new double[dim];
		double *Xprev = new double[dim];
		getMemory(J, dim);
		getMemoryforArr(arr, dim);
		concat(arr, J, dx, dim);
		vecF(F, X, Func, dim);
		Jac(X, J, Func, dim);
		concat(arr, J, F, dim);
		Gauss_method2(arr, dx, dim);
		copyArrays(Xprev, X, dim);
		for (int i = 0; i < dim; i++){
			X[i] += dx[i];
		}

		d1 = delta1(X, Func);
		d2 = delta2(X, Xprev, dim, k);
		output_line(k + 1, d1, d2);
		if (d1 < eps1 && d2 < eps2){
			cout << "\nX: " << setw(10) << X[0] << setw(10) << X[1] << endl;
			cout << "Func[0]: " << Func[0](X) << endl << "Func[1]: " << Func[1](X);
			_getch();
			return 1;
		}
	}
	freeMemory(J, dim);
	_getch();
	return 0;
}



