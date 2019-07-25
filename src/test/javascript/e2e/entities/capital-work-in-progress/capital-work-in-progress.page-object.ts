import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class CapitalWorkInProgressComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-capital-work-in-progress div table .btn-danger'));
  title = element.all(by.css('gha-capital-work-in-progress div h2#page-heading span')).first();

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

export class CapitalWorkInProgressUpdatePage {
  pageTitle = element(by.id('gha-capital-work-in-progress-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  transactionMonthInput = element(by.id('field_transactionMonth'));
  assetSerialTagInput = element(by.id('field_assetSerialTag'));
  serviceOutletCodeInput = element(by.id('field_serviceOutletCode'));
  transactionIdInput = element(by.id('field_transactionId'));
  transactionDetailsInput = element(by.id('field_transactionDetails'));
  transactionAmountInput = element(by.id('field_transactionAmount'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setTransactionMonthInput(transactionMonth) {
    await this.transactionMonthInput.sendKeys(transactionMonth);
  }

  async getTransactionMonthInput() {
    return await this.transactionMonthInput.getAttribute('value');
  }

  async setAssetSerialTagInput(assetSerialTag) {
    await this.assetSerialTagInput.sendKeys(assetSerialTag);
  }

  async getAssetSerialTagInput() {
    return await this.assetSerialTagInput.getAttribute('value');
  }

  async setServiceOutletCodeInput(serviceOutletCode) {
    await this.serviceOutletCodeInput.sendKeys(serviceOutletCode);
  }

  async getServiceOutletCodeInput() {
    return await this.serviceOutletCodeInput.getAttribute('value');
  }

  async setTransactionIdInput(transactionId) {
    await this.transactionIdInput.sendKeys(transactionId);
  }

  async getTransactionIdInput() {
    return await this.transactionIdInput.getAttribute('value');
  }

  async setTransactionDetailsInput(transactionDetails) {
    await this.transactionDetailsInput.sendKeys(transactionDetails);
  }

  async getTransactionDetailsInput() {
    return await this.transactionDetailsInput.getAttribute('value');
  }

  async setTransactionAmountInput(transactionAmount) {
    await this.transactionAmountInput.sendKeys(transactionAmount);
  }

  async getTransactionAmountInput() {
    return await this.transactionAmountInput.getAttribute('value');
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

export class CapitalWorkInProgressDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-capitalWorkInProgress-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-capitalWorkInProgress'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
