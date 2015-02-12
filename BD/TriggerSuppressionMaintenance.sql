CREATE TRIGGER supprimerMaintenance on MAINTENANCE
INSTEAD OF DELETE
AS BEGIN 
	declare @idMaintenance int
	DECLARE CursorMaintenance CURSOR FOR SELECT idMaintenance FROM DELETED
	OPEN CursorMaintenance
	FETCH CursorMaintenance INTO @idMaintenance
	WHILE @@FETCH_STATUS=0
	BEGIN
		DELETE estIntervenu WHERE idMaintenance=@idMaintenance
		DELETE estMaintenu WHERE idMaintenance=@idMaintenance
		DELETE MAINTENANCE WHERE idMaintenance=@idMaintenance
		FETCH CursorMaintenance INTO @idMaintenance
	END
	CLOSE CursorMaintenance
	DEALLOCATE CursorMaintenance
END