CREATE TRIGGER SuppressionMateriel on MATERIEL
INSTEAD OF DELETE
AS BEGIN 
	declare @idMateriel int;
	DECLARE CursorMateriel CURSOR FOR SELECT idMateriel FROM DELETED
	OPEN CursorMateriel
	FETCH CursorMateriel INTO @idMateriel
	WHILE @@FETCH_STATUS=0
	BEGIN
		DELETE FROM UTILISE WHERE idMateriel=@idMateriel
		DELETE FROM ESTMAINTENU WHERE idMateriel=@idMateriel
		DELETE FROM COMPOSE WHERE idMateriel=@idMateriel
		DELETE FROM ESTINSTALLE WHERE idMateriel=@idMateriel
		DELETE FROM MATERIEL WHERE idMateriel=@idMateriel
		FETCH CursorMateriel INTO @idMateriel
	END
	CLOSE CursorMateriel
	DEALLOCATE CursorMateriel
END