import scipy.io
import matplotlib.pyplot as plt
import numpy as np
from matplotlib import cm
plt.rcParams["font.family"] = "Linux Biolinum"
plt.rcParams.update({'font.size': 36,'font.weight':'bold','pdf.fonttype':42})
plt.rcParams["legend.handlelength"]=1.5
x1 = scipy.io.loadmat('../matlab/NormalBatteryBetterShape/QV1.mat')
time = np.transpose(x1['time'])[0]
power1 = np.transpose(x1['power1'])[0]
power2 = np.transpose(x1['power2'])[0]
power3 = np.transpose(x1['power3'])[0]
temperature = np.transpose(x1['T_record'])[0]

fig = plt.figure(figsize=(10,6))
ax1 = fig.add_axes([0.15,0.2,0.7,0.75])


ax1.plot(time,power1,'-',c='royalblue',linewidth=3.0,zorder=5,markersize=15,mfc='w',mec='royalblue',mew=1)
ax1.plot(time,power2,'-',c='darkred',linewidth=3.0,zorder=5,markersize=15,mfc='w',mec='darkred',mew=1)


ax1.fill_between(time, power1, power2, where=power2>power1, facecolor='darkred',alpha=1,label='Attack')
ax1.fill_between(time, power3, power2, where=(power2<power3), facecolor='green',alpha=0.5,label='Emergency',hatch="/")
ax1.fill_between(time, power1, power3, where=(power3<power1), facecolor='royalblue',alpha=0.5,label='Charge',hatch="oo")

ax1.set_xlim([150,220])
# ax1.set_xticks([170,190,200])
ax1.set_ylim([4, 10])
ax1.set_yticks([4,6,8,10])
ax1.yaxis.grid(zorder=-1,color='lightgray',linewidth=1,linestyle='--')
ax1.xaxis.grid(zorder=-1,color='lightgray',linewidth=1,linestyle='--')
ax1.set_xlabel('Time (min)',weight='bold')
ax1.set_ylabel('Power(kW)',weight='bold')
ax1.tick_params(axis='both',direction='out', length=10, width=3, colors='k', grid_alpha=1)

# ax1.annotate('Attacks', xy=(15, 8.3), xycoords='data', xytext=(-100, +60),
#              textcoords='offset points', fontsize=36,color='darkred',
#              arrowprops=dict(arrowstyle='-|>', color='darkred',lw=3.0,ls='-',connectionstyle="arc3,rad=-.2"))
# ax1.annotate('Charge', xy=(50, 7.5), xycoords='data', xytext=(-60, +90),
#              textcoords='offset points', fontsize=36,color='royalblue',
#              arrowprops=dict(arrowstyle='-|>', color='royalblue',lw=3.0,ls='-',connectionstyle="arc3,rad=-.2"))

ax2 = ax1.twinx()
ax2.plot(time,temperature,'-',c='orange',linewidth=3.0,zorder=5,markersize=15,mfc='w',mec='orange',mew=1)
ax2.set_ylim([25,55])
ax2.set_yticks([25,35,45,55])
ax2.tick_params(axis='y', colors='orange')
ax2.set_ylabel('Temperature ($^{\circ}$C)',weight='bold',color='orange')
ax2.spines['right'].set_color('orange')
ax2.tick_params(axis='y',direction='out', length=10, width=3, colors='orange', grid_alpha=1)

fig.legend(loc="best",ncol=2,bbox_to_anchor=(0.85,1),fontsize='small',frameon=False,handletextpad=0.1,labelspacing=0)

plt.tight_layout()
plt.show()

fig.savefig('../figures/normal_q_demo.pdf')
