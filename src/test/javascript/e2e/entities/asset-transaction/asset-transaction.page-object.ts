import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AssetTransactionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-asset-transaction div table .btn-danger'));
  title = element.all(by.css('gha-asset-transaction div h2#page-heading span')).first();

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

export class AssetTransactionUpdatePage {
  pageTitle = element(by.id('gha-asset-transaction-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  transactionReferenceInput = element(by.id('field_transactionReference'));
  transactionDateInput = element(by.id('field_transactionDate'));
  scannedDocumentIdInput = element(by.id('field_scannedDocumentId'));
  transactionApprovalIdInput = element(by.id('field_transactionApprovalId'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setTransactionReferenceInput(transactionReference) {
    await this.transactionReferenceInput.sendKeys(transactionReference);
  }

  async getTransactionReferenceInput() {
    return await this.transactionReferenceInput.getAttribute('value');
  }

  async setTransactionDateInput(transactionDate) {
    await this.transactionDateInput.sendKeys(transactionDate);
  }

  async getTransactionDateInput() {
    return await this.transactionDateInput.getAttribute('value');
  }

  async setScannedDocumentIdInput(scannedDocumentId) {
    await this.scannedDocumentIdInput.sendKeys(scannedDocumentId);
  }

  async getScannedDocumentIdInput() {
    return await this.scannedDocumentIdInput.getAttribute('value');
  }

  async setTransactionApprovalIdInput(transactionApprovalId) {
    await this.transactionApprovalIdInput.sendKeys(transactionApprovalId);
  }

  async getTransactionApprovalIdInput() {
    return await this.transactionApprovalIdInput.getAttribute('value');
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

export class AssetTransactionDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-assetTransaction-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-assetTransaction'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
