import scipy.io
import matplotlib.pyplot as plt
import numpy as np
from matplotlib import cm

# set some global paramter for figure
plt.rcParams["font.family"] = "Linux Biolinum"  #good font for ACM conference
plt.rcParams.update({'font.size': 36,'font.weight':'bold','pdf.fonttype':42}) #font size for confernce
plt.rcParams['hatch.linewidth'] = 3.0
plt.rcParams["legend.handlelength"]=1.5 #the line length in legend

x1 = scipy.io.loadmat('../matlab/FailureRate/componentFailure.mat') #import matlab data from file path
# get the data
T = x1['T']
Rate27 = x1['defaultRate']
Rate32 = x1['Rate32']
Rate35 = x1['Rate35']

RAM = x1['RAM'][0]  #add [0] in order to convert 2D array to 1D array
HDD = x1['HDD'][0]
MB = x1['MB'][0]
Ave = x1['Server'][0]
fig = plt.figure(figsize=(10,6))  #figure size, this is suitable for the font size 36
ax1 = fig.add_axes([0.15,0.2,0.8,0.75]) # plot region size.

# main content for bar plot
total_width, n = 0.5, 4   # n is how many grouds of bar
width = total_width / n
x = np.arange(3) - (total_width - width) / 2
labels = ['27', '32', '35']
ax1.bar(x, RAM,  width=width, label='RAM',lw=3.0,edgecolor='royalblue',facecolor='w')
ax1.bar(x + width, HDD, width=width, label='HDD',hatch='/',lw=3.0,edgecolor='orange',facecolor='w')
ax1.bar(x + 2 * width, MB, width=width, label='Motherboard',hatch='/\\',lw=3.0,edgecolor='darkgreen',facecolor='w')
ax1.bar(x + 3 * width, Ave, width=width, label='Server',hatch='+',lw=3.0,edgecolor='darkred',facecolor='w')
plt.xticks(x+1.5*width,labels)
ax1.yaxis.grid(zorder=-1,color='lightgray',linewidth=1,linestyle='--')
ax1.set_xlabel('Temperature ($^{\circ}$C)',weight='bold')
ax1.set_ylabel('AFR(%)',weight='bold')
plt.tight_layout()
ax1.set_axisbelow(True)
ax1.set_ylim(0,15)
for axis in ['top','bottom','left','right']:  #set width of all axises
  ax1.spines[axis].set_linewidth(3.0)
legend=fig.legend(loc='best',ncol=2,bbox_to_anchor=(0.8,0.95),fontsize='x-small',facecolor='w',edgecolor='k') # legend setting
legend.get_frame().set_linewidth(3.0)
fig.tight_layout()
# show and save as pdf
plt.show()
fig.savefig('../figures/componentFailure.pdf')
