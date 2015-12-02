
select * from actions order by sTime desc limit 10;
#Wed Oct 21 2015 21:55:58 GMT-0300 (BRT) -> sTime(1445475358112) -> 2429797

select count(*) from actions where sTime < 1409540400000 order by sTime asc limit 10;
#Mon Sep 01 2014 00:00:00 GMT-0300 (BRT) -> 1090

select count(*) from actions where sTime < 1420081200000 order by sTime asc limit 10;
#Thu Jan 01 2015 00:00:00 GMT-0300 (BRT) -> 5867

select count(*) from actions where sTime >= 1425178800000 AND sTime <= 1425351600000 order by sTime asc;


select * from actions where sTime >= 1420081200000 order by sTime asc limit 10;

select distinct(sJhm), count(1) from actions where sClient = 'IPMT' group by sJhm order by 2 desc;

select distinct(sSectionJhm), count(1) from actions where sClient = 'IPMT' and sJhm = '' group by sSectionJhm;

select distinct(sUrl), count(1) from actions where sClient = 'IPMT' and sJhm = '' group by sUrl;

select distinct(sActionType), count(1) from actions where sClient = 'IPMT' and sJhm = '' group by sActionType;

select distinct(sXPath), count(1) from actions where sClient = 'IPMT' and sJhm = '' group by sXPath;
