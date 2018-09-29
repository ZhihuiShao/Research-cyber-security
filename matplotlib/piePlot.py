import scipy.io
import matplotlib.pyplot as plt
import numpy as np
from matplotlib import cm
plt.rcParams["font.family"] = "Linux Biolinum"
plt.rcParams.update({'font.size': 36,'font.weight':'bold','pdf.fonttype':42})
plt.rcParams["legend.handlelength"]=1.5

labels = ['Google, Amazon...(7.8%)', 'Multi-tenant(37%)', 'Enterprise(53%)']
sizes = [7.8, 37, 53]
explode = (0, 0.1, 0)  # only "explode" the 2nd slice (i.e. 'Hogs')

fig = plt.figure(figsize=(12,6))
ax1 = fig.add_axes([0,0,0.4,1])
patches, texts = ax1.pie(sizes,explode=explode,startangle=90,colors=['g','dodgerblue','grey'])
patches[0].set_hatch('/')
patches[1].set_hatch('/\\')
patches[2].set_hatch('.')
plt.legend(patches, labels,loc="right",bbox_to_anchor=(2.5,0.80),fontsize='small',facecolor='w',edgecolor='k')
ax1.axis('equal')
fig.tight_layout()

plt.show()
fig.savefig('../figures/multi_tenant_frac.pdf')