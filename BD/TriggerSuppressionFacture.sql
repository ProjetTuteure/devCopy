CREATE TRIGGER supprimerFacture on FACTURE 
INSTEAD OF DELETE 
AS BEGIN 
	declare @idFacture int
	DECLARE CursorFacture CURSOR FOR SELECT idFacture FROM DELETED
	OPEN CursorFacture
	FETCH CursorFacture INTO @idFacture
	WHILE @@FETCH_STATUS=0
	BEGIN
		DELETE FROM LOGICIEL WHERE idFacture=@idFacture
		DELETE FROM MATERIEL WHERE idFacture=@idFacture
		DELETE FROM ESTINTERVENU WHERE idFacture=@idFacture
		DELETE FROM FACTURE WHERE idFacture=@idFacture
		FETCH CursorFacture INTO @idFacture
	END
	CLOSE CursorFacture
	DEALLOCATE CursorFacture
END
	