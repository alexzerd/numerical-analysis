#include <iomanip>
#include <cmath>
#include <iostream>
#include <conio.h>
#include <fstream>
using namespace std;

const double eps_add = 1e-3, Eps = 1e-9;
const double tau_max = 0.005;

const double k = 0.25;
const double a = 2;

typedef double(*Func) (double* u, double t);
typedef double(*Func2) (double* yk, double* yk_p1, double tauk, double tk_p1, double t_k);

double func1_1(double *u, double t){
    return u[1] - (u[0]*(a*u[0]+k*u[1]));
}

double func1_2(double *u, double t){
	return exp(u[0]) - u[0]*(u[0]+a*u[1]);
}

double F1(double yk[], double yk_p1[], double tauk, double tk_p1, double t_k)
{
	return yk_p1[0] - yk[0] - tauk * func1_1(yk_p1, t_k);
}

double F2(double yk[], double yk_p1[], double tauk, double tk_p1, double t_k)
{
	return yk_p1[1] - yk[1] - tauk * func1_2(yk_p1, t_k);
}
void Jakobi2(double** jakobi, double* args, Func2* m_func, double* vect, double* yk, double tauk, double tk_p1, double t_k, int n)
{
	int j;
	double* temp = new double[n];
	for (int k = 0; k<n; k++)
		temp[k] = args[k];

	for (int i = 0; i<n; i++)
	{
		for (j = 0; j<n; j++)
		{
			for (int k = 0; k<n; k++)
				temp[k] = args[k];

			temp[j] = args[j] + Eps;
			jakobi[i][j] = (m_func[i](yk, temp, tauk, tk_p1, t_k) - m_func[i](yk, args, tauk, tk_p1, t_k)) / Eps;
		}
		jakobi[i][j] = vect[i];
	}
	delete[] temp;
}

double Newton_method2(Func2* m_func, double* solution, double* args, double* yk, double tauk, double tk_p1, double t_k, int n){
	double* vect = new double[n];
	double** jakobi = new double*[n];
	for (int i = 0; i<n; i++)
	{
		jakobi[i] = new double[n + 1];
	}
	double* delta = new double[n];
	for (int i = 0; i < n; i++)
		vect[i] = (-1)*m_func[i](yk, args, tauk, tk_p1, t_k);

	double max = abs(vect[0]);
	for (int i = 0; i < n; i++)
	if (max < abs(vect[i]))
		max = vect[i];

	Jakobi2(jakobi, args, m_func, vect, yk, tauk, tk_p1, t_k, n);
	if (Gauss_method2(jakobi, delta, 2))
	{
		for (int i = 0; i < n; i++)
			solution[i] = args[i] + delta[i];


		delete[] vect;
		delete[] delta;
		for (int i = 0; i < n; i++)
			delete[] jakobi[i];
		delete[] jakobi;
		return max;
	}
	delete[] vect;
	delete[] delta;
	for (int i = 0; i < n; i++)
		delete[] jakobi[i];
	delete[] jakobi;
	cout << "Error in Newton" << endl;
	return 0;
}

double find_min_Tau_k(double* tau_k, int n)
{
	double min = tau_k[0];
	for (int j = 0; j < n; j++)
	{
		if (min > tau_k[j])
		{
			min = tau_k[j];
		}
	}

	return min;
}

bool Explicit_Euler(){
	ofstream fin("EE.txt");
	ofstream finu0("EE_u0.txt");
	ofstream finu1("EE_u1.txt");
	ofstream fintk("EE_tk.txt");

	int lenght = 13;
	double T = 5.0, u1 = 1, u2 = 0, t_k = 0;
	int  n = 2;
	Func func[] = { func1_1, func1_2};
	double *u = new double[n];
	double *vect = new double[n];
	u[0] = u1; u[1] = u2;
	double *tau_k = new double[n];
	double tau_min = 0.003;
	fin << setw(5) << "i";
	for (int i = 0; i < n; i++){
		fin << setw(lenght) << "u[" << i << "]";
	}
	fin << setw(lenght) << "t_k" << endl;
	int iter = 0;
	do
	{
		fin << setw(5) << iter++;
		for (int i = 0; i < n; i++)
		{
			vect[i] = func[i](u, t_k);
		}

		tau_min = eps_add / (abs(vect[0]) + (eps_add / tau_max));
		double temp = 0;
		for (int i = 1; i < n; i++)
		{
			temp = eps_add / (abs(vect[i]) + (eps_add / tau_max));
			if (temp < tau_min)
			{
				tau_min = temp;
			}
		}
		for (int i = 0; i < n; i++){
			u[i] += tau_min*vect[i];
			fin << setw(lenght) << u[i];
		}
		t_k += tau_min;
		fin << setw(lenght) << t_k << endl;
		finu0 << u[0] << endl;
		finu1 << u[1] << endl;
		fintk << t_k << endl;


	} while (t_k < T);
	fin.close();
	finu0.close();
	finu1.close();
	fintk.close();
	cout << "Explicit method complete.\n";
	return true;
}
bool Implict_Euler(){
	ofstream fin("IE.txt");
	ofstream finu0("IE_u0.txt");
	ofstream finu1("IE_u1.txt");
	ofstream fintk("IE_tk.txt");
	int lenght = 13;
	double T = 5.0, u1 = 1, t_kp1 = 0, u2 = 0, t_k = 0, tau_k = 0, tau_km1;
	double tau_min = 0.003;
	tau_k = tau_km1 = tau_min;
	int  n = 2, iter = 0, N = 150;
	double* y_k = new double[n];
	double* y_kp1 = new double[n];
	double* y_km1 = new double[n];
	double d1, d2;
	double* e_i_k = new double[n];
	Func2 func[] = { F1, F2 };
	double *vect = new double[n];
	y_k[0] = y_kp1[0] = y_km1[0] = u1;
	y_k[1] = y_kp1[1] = y_km1[1] = u2;
	fin << setw(5) << "i";
	for (int i = 0; i < n; i++){
		fin << setw(lenght) << "u[" << i << "]";
	}
	fin << setw(lenght) << "t_k" << endl;

	int iter1 = 0;
	do
	{
	    fin << setw(5) << iter1++;
		t_kp1 = t_k + tau_k;
		double* args = new double[n];
		for (int i = 0; i < n; i++)
			args[i] = y_k[i];

		double* solution = new double[n];
		for (int i = 0; i < n; i++)
		{
			solution[i] = 0;
		}
		do
		{
			d1 = Newton_method2(func, solution, args, y_k, tau_k, t_kp1, t_k, n);

			double max;
			if (abs(solution[0]) < 1) {
				max = abs(args[0] - solution[0]);
			}
			else {
				max = abs((args[0] - solution[0]) / solution[0]);
			}

			for (int i = 0; i < n; i++)
			if (abs(solution[i]) < 1)
			{
				if (max < abs(args[i] - solution[i]))
					max = abs(args[i] - solution[i]);
			}
			else max = abs((args[i] - solution[i]) / solution[i]);

			d2 = max;

			for (int i = 0; i<n; i++)
				args[i] = solution[i];

			iter++;

		} while ((abs(d1)>Eps) || (abs(d2) > Eps) && (iter <= N));

		for (int l = 0; l<n; l++)
			y_kp1[l] = solution[l];

		for (int i = 0; i < n; i++)
		{
			e_i_k[i] = -1 * (tau_k / (tau_k + tau_km1)) * (y_kp1[i] - y_k[i] - ((tau_k / tau_km1) * (y_k[i] - y_km1[i])));
		}


		bool stop = false;

		for (int i = 0; i < n; i++){
			if (abs(e_i_k[i]) > eps_add)
			{
				stop = true;
				break;
			}
		}

		if (stop)
		{
			tau_k = tau_k / 2;
			t_kp1 = t_k;
			for (int i = 0; i < n; i++)
				y_kp1[i] = y_k[i];
			continue;
		}

		double* tau_i_kp1 = new double[n];

		for (int i = 0; i < n; i++)
		{
			tau_i_kp1[i] = sqrt(eps_add / (abs(e_i_k[i])) * tau_k);
		}

		double tau_kp1 = find_min_Tau_k(tau_i_kp1, n);

		if (tau_kp1 > tau_max)
		{
			tau_kp1 = tau_max;
		}

		fin << setw(lenght) << y_kp1[0] << setw(lenght) << y_kp1[1] << setw(lenght) << t_kp1 << endl;
		finu0 << y_kp1[0] << endl;
		finu1 << y_kp1[1] << endl;
		fintk << t_kp1 << endl;

		for (int i = 0; i < n; i++)
		{
			y_km1[i] = y_k[i];
			y_k[i] = y_kp1[i];
		}

		tau_km1 = tau_k;
		tau_k = tau_kp1;
		t_k = t_kp1;
	} while (t_k < T);
	fin.close();
	finu0.close();
	finu1.close();
	fintk.close();
	cout << "Implict method complete.\n";
	return true;
}

bool systemOfDifEq(){

    Explicit_Euler();
    Implict_Euler();
}
