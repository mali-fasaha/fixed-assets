import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class FixedAssetItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-fixed-asset-item div table .btn-danger'));
  title = element.all(by.css('gha-fixed-asset-item div h2#page-heading span')).first();

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

export class FixedAssetItemUpdatePage {
  pageTitle = element(by.id('gha-fixed-asset-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  serviceOutletCodeInput = element(by.id('field_serviceOutletCode'));
  assetCategoryCodeInput = element(by.id('field_assetCategoryCode'));
  assetCategoryInput = element(by.id('field_assetCategory'));
  fixedAssetSerialCodeInput = element(by.id('field_fixedAssetSerialCode'));
  fixedAssetDescriptionInput = element(by.id('field_fixedAssetDescription'));
  purchaseDateInput = element(by.id('field_purchaseDate'));
  purchaseCostInput = element(by.id('field_purchaseCost'));
  purchaseTransactionIdInput = element(by.id('field_purchaseTransactionId'));
  ownershipDocumentIdInput = element(by.id('field_ownershipDocumentId'));
  assetPictureInput = element(by.id('file_assetPicture'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setServiceOutletCodeInput(serviceOutletCode) {
    await this.serviceOutletCodeInput.sendKeys(serviceOutletCode);
  }

  async getServiceOutletCodeInput() {
    return await this.serviceOutletCodeInput.getAttribute('value');
  }

  async setAssetCategoryCodeInput(assetCategoryCode) {
    await this.assetCategoryCodeInput.sendKeys(assetCategoryCode);
  }

  async getAssetCategoryCodeInput() {
    return await this.assetCategoryCodeInput.getAttribute('value');
  }

  async setAssetCategoryInput(assetCategory) {
    await this.assetCategoryInput.sendKeys(assetCategory);
  }

  async getAssetCategoryInput() {
    return await this.assetCategoryInput.getAttribute('value');
  }

  async setFixedAssetSerialCodeInput(fixedAssetSerialCode) {
    await this.fixedAssetSerialCodeInput.sendKeys(fixedAssetSerialCode);
  }

  async getFixedAssetSerialCodeInput() {
    return await this.fixedAssetSerialCodeInput.getAttribute('value');
  }

  async setFixedAssetDescriptionInput(fixedAssetDescription) {
    await this.fixedAssetDescriptionInput.sendKeys(fixedAssetDescription);
  }

  async getFixedAssetDescriptionInput() {
    return await this.fixedAssetDescriptionInput.getAttribute('value');
  }

  async setPurchaseDateInput(purchaseDate) {
    await this.purchaseDateInput.sendKeys(purchaseDate);
  }

  async getPurchaseDateInput() {
    return await this.purchaseDateInput.getAttribute('value');
  }

  async setPurchaseCostInput(purchaseCost) {
    await this.purchaseCostInput.sendKeys(purchaseCost);
  }

  async getPurchaseCostInput() {
    return await this.purchaseCostInput.getAttribute('value');
  }

  async setPurchaseTransactionIdInput(purchaseTransactionId) {
    await this.purchaseTransactionIdInput.sendKeys(purchaseTransactionId);
  }

  async getPurchaseTransactionIdInput() {
    return await this.purchaseTransactionIdInput.getAttribute('value');
  }

  async setOwnershipDocumentIdInput(ownershipDocumentId) {
    await this.ownershipDocumentIdInput.sendKeys(ownershipDocumentId);
  }

  async getOwnershipDocumentIdInput() {
    return await this.ownershipDocumentIdInput.getAttribute('value');
  }

  async setAssetPictureInput(assetPicture) {
    await this.assetPictureInput.sendKeys(assetPicture);
  }

  async getAssetPictureInput() {
    return await this.assetPictureInput.getAttribute('value');
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

export class FixedAssetItemDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-fixedAssetItem-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-fixedAssetItem'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
