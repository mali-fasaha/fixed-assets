import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class CwipTransferComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-cwip-transfer div table .btn-danger'));
  title = element.all(by.css('gha-cwip-transfer div h2#page-heading span')).first();

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

export class CwipTransferUpdatePage {
  pageTitle = element(by.id('gha-cwip-transfer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  transferMonthInput = element(by.id('field_transferMonth'));
  assetSerialTagInput = element(by.id('field_assetSerialTag'));
  serviceOutletCodeInput = element(by.id('field_serviceOutletCode'));
  transferTransactionIdInput = element(by.id('field_transferTransactionId'));
  assetCategoryIdInput = element(by.id('field_assetCategoryId'));
  cwipTransactionIdInput = element(by.id('field_cwipTransactionId'));
  transferDetailsInput = element(by.id('field_transferDetails'));
  transferAmountInput = element(by.id('field_transferAmount'));
  dealerIdInput = element(by.id('field_dealerId'));
  transactionInvoiceIdInput = element(by.id('field_transactionInvoiceId'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setTransferMonthInput(transferMonth) {
    await this.transferMonthInput.sendKeys(transferMonth);
  }

  async getTransferMonthInput() {
    return await this.transferMonthInput.getAttribute('value');
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

  async setTransferTransactionIdInput(transferTransactionId) {
    await this.transferTransactionIdInput.sendKeys(transferTransactionId);
  }

  async getTransferTransactionIdInput() {
    return await this.transferTransactionIdInput.getAttribute('value');
  }

  async setAssetCategoryIdInput(assetCategoryId) {
    await this.assetCategoryIdInput.sendKeys(assetCategoryId);
  }

  async getAssetCategoryIdInput() {
    return await this.assetCategoryIdInput.getAttribute('value');
  }

  async setCwipTransactionIdInput(cwipTransactionId) {
    await this.cwipTransactionIdInput.sendKeys(cwipTransactionId);
  }

  async getCwipTransactionIdInput() {
    return await this.cwipTransactionIdInput.getAttribute('value');
  }

  async setTransferDetailsInput(transferDetails) {
    await this.transferDetailsInput.sendKeys(transferDetails);
  }

  async getTransferDetailsInput() {
    return await this.transferDetailsInput.getAttribute('value');
  }

  async setTransferAmountInput(transferAmount) {
    await this.transferAmountInput.sendKeys(transferAmount);
  }

  async getTransferAmountInput() {
    return await this.transferAmountInput.getAttribute('value');
  }

  async setDealerIdInput(dealerId) {
    await this.dealerIdInput.sendKeys(dealerId);
  }

  async getDealerIdInput() {
    return await this.dealerIdInput.getAttribute('value');
  }

  async setTransactionInvoiceIdInput(transactionInvoiceId) {
    await this.transactionInvoiceIdInput.sendKeys(transactionInvoiceId);
  }

  async getTransactionInvoiceIdInput() {
    return await this.transactionInvoiceIdInput.getAttribute('value');
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

export class CwipTransferDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-cwipTransfer-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-cwipTransfer'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
