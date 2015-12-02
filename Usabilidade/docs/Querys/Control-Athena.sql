 
 #treinamento 	- 28 (14-16)
 #experimento 	- 29 (apenas 1)
 #obs 			- 30 (16-17)
 #obs.mestrado 	- 32 (3)
 
 #relatorio de ações realizadas em cada tarefa, por cada usuário
 select us.nome, ta.id, ta.nome, count(a.id) from action a
 left join fluxo_action fla on a.id = fla.acoes_id
 left join fluxo flu on flu.id = fla.Fluxo_id
 left join tarefa ta on ta.id = flu.tarefa_id
 left join teste te on te.id = ta.teste_id
 left join usuario us on us.id = flu.usuario_id
 where te.id in (28)
 group by flu.usuario_id, ta.nome
 order by count(a.id) asc;
 
 #tipo de acoes mais realizadas ao logar
select distinct(a.sActionType), count(a.id) from action a
 left join fluxo_action fla on a.id = fla.acoes_id
 left join fluxo flu on flu.id = fla.Fluxo_id
 left join tarefa ta on ta.id = flu.tarefa_id
 left join teste te on te.id = ta.teste_id
 left join usuario us on us.id = flu.usuario_id
 where te.id in (28) 
 and ta.id = 33
 and a.sActionType not in ('mouseover', 'pular', 'concluir', 'onload')
 group by a.sActionType
 order by count(a.id) asc;
 
 