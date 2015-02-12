CREATE TRIGGER SuppressionPrestataire on PRESTATAIRE
INSTEAD OF DELETE
AS BEGIN 
	declare @idPrestataire int;
	DECLARE CursorPrestataire CURSOR FOR SELECT idPrestataire FROM DELETED
	OPEN CursorPrestataire
	FETCH CursorPrestataire INTO @idPrestataire
	WHILE @@FETCH_STATUS=0
	BEGIN
		DELETE FROM ESTINTERVENU WHERE idPrestataire=@idPrestataire
		DELETE FROM PRESTATAIRE WHERE idPrestataire=@idPrestataire
		FETCH CursorPrestataire INTO @idPrestataire
	END
	CLOSE CursorPrestataire
	DEALLOCATE CursorPrestataire
END