import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class FileUploadComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-file-upload div table .btn-danger'));
  title = element.all(by.css('gha-file-upload div h2#page-heading span')).first();

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

export class FileUploadUpdatePage {
  pageTitle = element(by.id('gha-file-upload-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descriptionInput = element(by.id('field_description'));
  fileNameInput = element(by.id('field_fileName'));
  periodFromInput = element(by.id('field_periodFrom'));
  periodToInput = element(by.id('field_periodTo'));
  fileTypeIdInput = element(by.id('field_fileTypeId'));
  dataFileInput = element(by.id('file_dataFile'));
  uploadSuccessfulInput = element(by.id('field_uploadSuccessful'));
  uploadProcessedInput = element(by.id('field_uploadProcessed'));
  uploadTokenInput = element(by.id('field_uploadToken'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setFileNameInput(fileName) {
    await this.fileNameInput.sendKeys(fileName);
  }

  async getFileNameInput() {
    return await this.fileNameInput.getAttribute('value');
  }

  async setPeriodFromInput(periodFrom) {
    await this.periodFromInput.sendKeys(periodFrom);
  }

  async getPeriodFromInput() {
    return await this.periodFromInput.getAttribute('value');
  }

  async setPeriodToInput(periodTo) {
    await this.periodToInput.sendKeys(periodTo);
  }

  async getPeriodToInput() {
    return await this.periodToInput.getAttribute('value');
  }

  async setFileTypeIdInput(fileTypeId) {
    await this.fileTypeIdInput.sendKeys(fileTypeId);
  }

  async getFileTypeIdInput() {
    return await this.fileTypeIdInput.getAttribute('value');
  }

  async setDataFileInput(dataFile) {
    await this.dataFileInput.sendKeys(dataFile);
  }

  async getDataFileInput() {
    return await this.dataFileInput.getAttribute('value');
  }

  getUploadSuccessfulInput(timeout?: number) {
    return this.uploadSuccessfulInput;
  }
  getUploadProcessedInput(timeout?: number) {
    return this.uploadProcessedInput;
  }
  async setUploadTokenInput(uploadToken) {
    await this.uploadTokenInput.sendKeys(uploadToken);
  }

  async getUploadTokenInput() {
    return await this.uploadTokenInput.getAttribute('value');
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

export class FileUploadDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-fileUpload-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-fileUpload'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
