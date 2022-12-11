from math import log as ln, e
from random import random as xi_i
from scipy.stats import chi2_contingency as chi2
import matplotlib.pyplot as plt
import numpy as np


lamda_list = [0.5, 1, 1.5]
amount = 10_000

x_i_0 = [-ln(xi_i())/lamda_list[0] for _ in range(amount)]
x_i_1 = [-ln(xi_i())/lamda_list[1] for _ in range(amount)]
x_i_2 = [-ln(xi_i())/lamda_list[2] for _ in range(amount)]

fx_0 = [1 - e**(-lamda_list[0] * x) for x in x_i_0]
fx_1 = [1 - e**(-lamda_list[1] * x) for x in x_i_1]
fx_2 = [1 - e**(-lamda_list[2] * x) for x in x_i_2]

chi2_0, prob_0, df_0, expected_0 = chi2([x_i_0, fx_0])
chi2_1, prob_1, df_1, expected_1 = chi2([x_i_1, fx_1])
chi2_2, prob_2, df_2, expected_2 = chi2([x_i_1, fx_1])

print(f"The test statistic 0: {chi2_0}\nDegrees of freedom 0: {df_0}\nThe p-value of the test 0: {prob_0}\n")
print(f"The test statistic 1: {chi2_1}\nDegrees of freedom 1: {df_1}\nThe p-value of the test 1: {prob_1}\n")
print(f"The test statistic 2: {chi2_2}\nDegrees of freedom 2: {df_2}\nThe p-value of the test 2: {prob_2}\n")

print(f"The expected frequencies, based on the marginal sums of the table 0: {expected_0}\n")
print(f"The expected frequencies, based on the marginal sums of the table 1: {expected_1}\n")
print(f"The expected frequencies, based on the marginal sums of the table 2: {expected_2}\n")

hist_x0 = plt.figure(0)
ax_0 = hist_x0.add_subplot()
ax_0.set_title(f"mean: {np.mean(x_i_0)}\naverage: {np.average(x_i_0)}")
ax_0.hist(x_i_0, edgecolor='black') 

hist_x1 = plt.figure(1)
ax_1 = hist_x1.add_subplot()
ax_1.set_title(f"mean: {np.mean(x_i_1)}\naverage: {np.average(x_i_1)}")
ax_1.hist(x_i_1, edgecolor='black') 

hist_x2 = plt.figure(2)
ax_2 = hist_x2.add_subplot()
ax_2.set_title(f"mean: {np.mean(x_i_2)}\naverage: {np.average(x_i_2)}")
ax_2.hist(x_i_2, edgecolor='black') 

plt.show()
