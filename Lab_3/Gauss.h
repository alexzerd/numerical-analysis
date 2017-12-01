#include <iostream>
#include <conio.h>
#include <iomanip>
#include <cmath>
using namespace std;

const double eps = 1e-9;

void output(int n, int m, double **a)
{
	cout << "n = " << n << ";  m = " << m << endl << endl;
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < m; j++)
		{
			cout << setw(10) << a[i][j];
		}
		cout << endl;
	}
}

void output2(int dim, double **A, double *B){
	cout << "Dim = " << dim << endl << endl;
	for (int i = 0; i < dim; i++)
	{
		for (int j = 0; j < dim; j++)
		{
			cout << setw(10) << A[i][j];
		}
		cout << setw(10) << B[i];
		cout << endl;
	}
}

int find_max_el(int n, int j, double **a) // где j - номер столбца, в котором искать макс элемент
{
	int  max_el_j = j;
	double max_el = abs(a[j][j]);
	for (int k = j + 1; k < n; k++)
	{
		if (abs(a[k][j]) > max_el)
		{
			max_el = abs(a[k][j]);
			max_el_j = k;
		}
	}
	if (abs(max_el) < eps)
	{
		cout << "All elements in " << j + 1 << " column are equal to 0. Matrix is confluent";
		return -1;
	}
	return max_el_j;
}

bool swap_raws(int n, int m, int j, double **a){
	int pivot = find_max_el(n, j, a);
	if (pivot == -1)
	{
		return false;
	}
	if (pivot != j){
		double *a_temp = new double[m + 1];
		for (int i = j; i <= m; i++)
		{
			a_temp[i] = a[pivot][i];
			a[pivot][i] = a[j][i];
			a[j][i] = a_temp[i];
		}
		delete[]a_temp;
	}
	return true;
}

void reduction(int n, int m, int i, double **a){
	double pivot_value = a[i][i];
	for (int j = i; j <= m; j++)
	{
		a[i][j] /= pivot_value;
	}
}

void diminution(int n, int m, int i, double **a){
	for (int k = i + 1; k < n; k++)
	{
		double pivot_value = a[k][i];
		for (int j = i; j <= m; j++)
		{
			a[k][j] -= a[i][j] * pivot_value;
		}
	}
}

void vector(int n, int m, double **A, double *B, double *X, double *F)
{
	double *Ax = new double[n];
	for (int j = 0; j < n; j++){
		Ax[j] = 0;
	}

	for (int j = 0; j < n; j++){
		for (int i = 0; i < m; i++){
			Ax[j] += A[j][i] * X[i];
		}
	}

	for (int i = 0; i < n; i++)
	{
		F[i] = Ax[i] - B[i];
	}
}

void create_arr(int n, int m, double **arr){

}

int Gauss_method(){
	int n, m;   // ввод числа строк, столбцов и потом всех коэффициентов
	cout << "Enter number of rows n = ";
	cin >> n;
	cout << "Enter number of colomns m = ";
	cin >> m;
	int i = 0, j = 0;
	double **a = new double*[n];
	for (i = 0; i < n; i++)
	{
		a[i] = new double[m + 1];
	}
	/*for (i = 0; i < n; i++)
	{
	for (j = 0; j < m; j++)
	{
	cout << "a[" << i << "][" << j << "]: ";
	cin >> a[i][j];
	}
	cout << "b[" << i << "]:    ";
	cin >> a[i][j];
	cout << endl;
	}*/

	//double arr[3][4] = { { 6, 13, -17, 2 }, { 13, 29, -38, 4 }, { -17, -38, 50, -5 } };
	double arr[3][4] = { { 1, 2, 1, 1 }, { -1, -2, 2, 1 }, { 0, 1, 1, 2 } };

	for (int k = 0; k < 3; k++)
	{
		for (int l = 0; l < 4; l++)
		{
			a[k][l] = arr[k][l];
		}
	}
	/*for (i = 0; i < n; i++)
	{
	for (j = 0; j < m; j++)
	{
	cout << "a[" << i << "][" << j << "]: ";
	cin >> a[i][j];
	}
	cout << "b[" << i << "]:    ";
	cin >> a[i][j];
	cout << endl;
	}*/

	double *B = new double[n];
	double **A = new double*[n];
	for (i = 0; i < n; i++)
	{
		A[i] = new double[m];
	}
	for (int i = 0; i < n; i++)
	{
		B[i] = a[i][m];
		for (int j = 0; j < m; j++)
		{
			A[i][j] = a[i][j];
		}
	}

	//system("cls"); //  вывод матрицы (с предварительной очисткой экрана от разного мусора)
	cout << "Matrix of coefficients and B:\n";
	output(n, m + 1, a);

	for (int i = 0; i < m; i++){  //метод Гаусса
		if (!swap_raws(n, m, i, a))
		{
			_getch();
			return -1;  // если все коэффициенты в основном столбце i квадрата = 0, то программа завершается
		};
		reduction(n, m, i, a);
		diminution(n, m, i, a);
	}
	cout << "\nMatrix after Gauss method:\n";
	output(n, m + 1, a);

	double *x = new double[m];
	for (int i = 0; i < m; i++)
	{
		x[i] = 0;
	}
	for (int i = n - 1; i >= 0; i--)	// обратный ход Гаусса
	{
		x[i] = a[i][m];
		for (int j = m - 1; j > i; j--)
		{
			x[i] -= x[j] * a[i][j];
		}
	}

	cout << "X:\n";
	for (int i = 0; i < m; i++)
	{
		cout << x[i] << endl;
	}

	//вектор невязки
	double *F = new double[n];
	for (int i = 0; i < n; i++)
	{
		F[i] = 0;
	}
	vector(n, m, A, B, x, F);
	cout << "\n	Residual vector:\n";
	double d = 0;
	for (int i = 0; i < n; i++)
	{
		cout << F[i] << endl;
		if (abs(F[i]) > d)
		{
			d = abs(F[i]);
		}
	}
	cout << "Norm d = " << d << endl;
	_getch();
}


bool Gauss_method2(double **A, double *&x, int dim){
	int i = 0;
	for (int i = 0; i < dim; i++){  //метод Гаусса
		if (!swap_raws(dim, dim, i, A))
		{
			_getch();
			return false;  // если все коэффициенты в основном столбце i квадрата = 0, то программа завершается
		};
		reduction(dim, dim, i, A);
		diminution(dim, dim, i, A);
	}


	for (int i = 0; i < dim; i++)
	{
		x[i] = 0;
	}
	for (int i = dim - 1; i >= 0; i--)	// обратный ход Гаусса
	{
		x[i] = A[i][dim];
		for (int j = dim; j > i; j--)
		{
			x[i] -= x[j] * A[i][j];
		}
	}
	return true;
}
