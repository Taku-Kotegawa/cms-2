create view v_employee as
select
	e.emp_id ,
	e."version" ,
	e.status ,
	e.created_by ,
	e.created_date ,
	e.last_modified_by ,
	e.last_modified_date ,
	e.emp_name ,
	e."password" ,
	e.email ,
	e.organization_id ,
	o.group_name ,
	o.director_id ,
	o.parent_id ,
	e.position_id ,
	p.position_name
from
	employee e
left outer join organization o
     on o.organization_id = e.organization_id
left outer join "position" p
     on	p.position_id = e.position_id
;