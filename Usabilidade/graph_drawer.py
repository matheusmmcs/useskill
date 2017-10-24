import sys
import pygraphviz as pgv

print "Lendo o arquivo..."

# le o arquivo 
try:
  arquivo = open(sys.argv[-1], 'r')
except:
  raw_input('Erro na leitura do arquivo!')
  exit(0)
linhas = arquivo.readlines();
arquivo.close()

pares = linhas[0].replace('\n', '').replace('(', '').replace(')', '').split(',')

vertices = []
for par in pares:
	vertices.append(par.split(':'))

# desenha o grafo
G=G=pgv.AGraph(strict=False,directed=True)
for vertice in vertices:
  G.add_edge(vertice[0], vertice[1])
#  G.add_edge(vertice[0], vertice[1], label=(vertice[0] + vertice[1]))

print "Posicionando vertices..."

G.layout()
#G.layout(prog='dot')

print "Desenhando o grafo..."

G.draw('grafo.png')
#G.draw('grafo.pdf')

print "Grafo gerado. Procure no diretorio pelo arquivo \"grafo\""