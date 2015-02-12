CREATE TRIGGER supprimerUtilisateur on UTILISATEUR
INSTEAD OF DELETE
AS BEGIN
	declare @idUtilisateur int
	DECLARE CursorUtilisateur CURSOR FOR SELECT idUtilisateur FROM DELETED
	OPEN CursorUtilisateur
	FETCH CursorUtilisateur INTO @idUtilisateur
	WHILE @@FETCH_STATUS=0
	BEGIN
		DELETE UTILISE WHERE idUtilisateur=@idUtilisateur
		DELETE UTILISATEUR WHERE idUtilisateur=@idUtilisateur
		FETCH CursorUtilisateur INTO @idUtilisateur
	END
	CLOSE CursorUtilisateur
	DEALLOCATE CursorUtilisateur
END