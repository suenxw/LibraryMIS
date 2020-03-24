create trigger tri_after on TB_Borrow for update
as
	declare @borrowID int
	declare @OverDay int
	declare @rdID int
	select @borrowID = borrowID from inserted
	select @rdID = rdID from inserted
	select @OverDay = DATEDIFF(day,DateRetPlan,DateRetAct) from inserted
	if(@OverDay<0)
		update TB_Borrow set OverDay = 0 where borrowID = @borrowID
	else
		update TB_Borrow set OverDay = @OverDay where borrowID = @borrowID
		update TB_Borrow set PunishMoney = 
		OverDay*(select PunishRate from TB_ReaderType where rdType = (select rdType from TB_Reader where rdID = @rdID) )
drop trigger tri_after