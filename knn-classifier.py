import matplotlib.pyplot as plt
import numpy as np
import operator
########################################
def gerar_data(): 
    dataSet = np.array([[1.0,5.0],[5.0,1.0],[5.0,6.0],[12.0,5.0],[98.0,78.0],[45.0,98.0],[78.0,98.0],[40.0,40.0],[35.0,30.0],[40.0,50.0]])
    labels = np.array(['r','r','r','r','b','b','b','y','y','y'])
    return dataSet, labels
########################################
def distancia_euclidiana(a, b):
    soma = 0.0# print('calculando distancia entre {} e {}'.format(a, b))
    for i in range(0, len(a)) :
        soma = soma + (a[i] - b[i])**2# subtrai componentes
    return soma**(1/2.0)# tira a raiz
########################################
def mapeia_labels(somelist):
    return {x[0]: 0 for x in somelist}
########################################
def plota_raio_distancia(x, r, classe):
    # plota o raio 
    circle1 = plt.Circle((x[0], x[1]), r, fill=False, color=classe, linestyle='--')
    fig = plt.gcf()
    ax = fig.gca()
    ax.add_artist(circle1)
    # plota o ponto em si
    plt.scatter(x[0], x[1], c=classe, marker='x', s=200)
########################################
def kdd(x, dataSet, labels ,k):
    dis = np.zeros(len(dataSet[:,0]))# distancias
    # calcula distancias
    for i in range(0, len(dis)):
        dis[i] = distancia_euclidiana(dataSet[i], x)
    # mapeia e ordena distancias
    dis = list(zip(dis, labels))
    dis = sorted(dis)
    # pega os k mais proximos e faz a contagem das classes
    classes = mapeia_labels(labels)
    for i in range(0, k):
        classes[dis[i][1]] = classes[dis[i][1]] + 1
    # pega o mais frequente
    classe = max(classes.iteritems(), key=operator.itemgetter(1))[0]
    # plota o raio de distancia mais externo (k-1)
    plota_raio_distancia(x, dis[k-1][0], classe)
    # retorna o mais frequente
    return classe
########################################
dataSet, labels = gerar_data()
# plota data de treino
plt.scatter(dataSet[:,0], dataSet[:,1], c=labels)
plt.title("Scatter Plot de Clusters")
plt.xlabel("Variavel X")
plt.ylabel("Variavel Y")
# cria um ponto aleatorio entre 0 e 100
#inx = float(input())
#iny = float(input())
novo_ponto = np.random.randint(100, size=2)# [inx, iny]
# plota e classifica o ponto
kdd(novo_ponto, dataSet, labels, 3)
plt.show()
########################################
