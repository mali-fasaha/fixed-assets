import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class FileTypeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-file-type div table .btn-danger'));
  title = element.all(by.css('gha-file-type div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getText();
  }
}

export class FileTypeUpdatePage {
  pageTitle = element(by.id('gha-file-type-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  fileTypeNameInput = element(by.id('field_fileTypeName'));
  fileMediumTypeSelect = element(by.id('field_fileMediumType'));
  descriptionInput = element(by.id('field_description'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setFileTypeNameInput(fileTypeName) {
    await this.fileTypeNameInput.sendKeys(fileTypeName);
  }

  async getFileTypeNameInput() {
    return await this.fileTypeNameInput.getAttribute('value');
  }

  async setFileMediumTypeSelect(fileMediumType) {
    await this.fileMediumTypeSelect.sendKeys(fileMediumType);
  }

  async getFileMediumTypeSelect() {
    return await this.fileMediumTypeSelect.element(by.css('option:checked')).getText();
  }

  async fileMediumTypeSelectLastOption(timeout?: number) {
    await this.fileMediumTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class FileTypeDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-fileType-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-fileType'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
