import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AssetAcquisitionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-asset-acquisition div table .btn-danger'));
  title = element.all(by.css('gha-asset-acquisition div h2#page-heading span')).first();

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

export class AssetAcquisitionUpdatePage {
  pageTitle = element(by.id('gha-asset-acquisition-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descriptionInput = element(by.id('field_description'));
  acquisitionMonthInput = element(by.id('field_acquisitionMonth'));
  assetSerialInput = element(by.id('field_assetSerial'));
  serviceOutletCodeInput = element(by.id('field_serviceOutletCode'));
  acquisitionTransactionIdInput = element(by.id('field_acquisitionTransactionId'));
  assetCategoryIdInput = element(by.id('field_assetCategoryId'));
  purchaseAmountInput = element(by.id('field_purchaseAmount'));
  assetDealerIdInput = element(by.id('field_assetDealerId'));
  assetInvoiceIdInput = element(by.id('field_assetInvoiceId'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setAcquisitionMonthInput(acquisitionMonth) {
    await this.acquisitionMonthInput.sendKeys(acquisitionMonth);
  }

  async getAcquisitionMonthInput() {
    return await this.acquisitionMonthInput.getAttribute('value');
  }

  async setAssetSerialInput(assetSerial) {
    await this.assetSerialInput.sendKeys(assetSerial);
  }

  async getAssetSerialInput() {
    return await this.assetSerialInput.getAttribute('value');
  }

  async setServiceOutletCodeInput(serviceOutletCode) {
    await this.serviceOutletCodeInput.sendKeys(serviceOutletCode);
  }

  async getServiceOutletCodeInput() {
    return await this.serviceOutletCodeInput.getAttribute('value');
  }

  async setAcquisitionTransactionIdInput(acquisitionTransactionId) {
    await this.acquisitionTransactionIdInput.sendKeys(acquisitionTransactionId);
  }

  async getAcquisitionTransactionIdInput() {
    return await this.acquisitionTransactionIdInput.getAttribute('value');
  }

  async setAssetCategoryIdInput(assetCategoryId) {
    await this.assetCategoryIdInput.sendKeys(assetCategoryId);
  }

  async getAssetCategoryIdInput() {
    return await this.assetCategoryIdInput.getAttribute('value');
  }

  async setPurchaseAmountInput(purchaseAmount) {
    await this.purchaseAmountInput.sendKeys(purchaseAmount);
  }

  async getPurchaseAmountInput() {
    return await this.purchaseAmountInput.getAttribute('value');
  }

  async setAssetDealerIdInput(assetDealerId) {
    await this.assetDealerIdInput.sendKeys(assetDealerId);
  }

  async getAssetDealerIdInput() {
    return await this.assetDealerIdInput.getAttribute('value');
  }

  async setAssetInvoiceIdInput(assetInvoiceId) {
    await this.assetInvoiceIdInput.sendKeys(assetInvoiceId);
  }

  async getAssetInvoiceIdInput() {
    return await this.assetInvoiceIdInput.getAttribute('value');
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

export class AssetAcquisitionDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-assetAcquisition-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-assetAcquisition'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
