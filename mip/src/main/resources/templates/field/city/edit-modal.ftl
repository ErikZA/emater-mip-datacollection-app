<div id="editModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form action="${urlUpdate}">
					<div class="modal-header">						
						<h4 class="modal-title">Alterar ${entity}</h4>
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">					
						<div class="form-group">
							<label>Nome</label>
							<input type="text" class="form-control" id="edit-name" name="name" required>
							<input type="hidden" class="form-control" id="edit-id" name="id">
						</div>
						<div class="form-group">
							<label>Região</label>
							<select name="regionId" class="form-control">
								<#list regions as region>
  									<option value="${region.id}">${region.name}</option>
								</#list>
  							</select>
						</div>
					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal" value="Cancelar">
						<input type="submit" class="btn btn-info" value="Salvar Alterações">
					</div>
				</form>
			</div>
		</div>
	</div>