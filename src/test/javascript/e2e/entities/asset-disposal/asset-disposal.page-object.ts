import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AssetDisposalComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-asset-disposal div table .btn-danger'));
  title = element.all(by.css('gha-asset-disposal div h2#page-heading span')).first();

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

export class AssetDisposalUpdatePage {
  pageTitle = element(by.id('gha-asset-disposal-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descriptionInput = element(by.id('field_description'));
  disposalDateInput = element(by.id('field_disposalDate'));
  assetCategoryIdInput = element(by.id('field_assetCategoryId'));
  assetItemIdInput = element(by.id('field_assetItemId'));
  disposalProceedsInput = element(by.id('field_disposalProceeds'));
  netBookValueInput = element(by.id('field_netBookValue'));
  profitOnDisposalInput = element(by.id('field_profitOnDisposal'));
  scannedDocumentIdInput = element(by.id('field_scannedDocumentId'));
  assetDealerIdInput = element(by.id('field_assetDealerId'));
  assetPictureInput = element(by.id('file_assetPicture'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setDisposalDateInput(disposalDate) {
    await this.disposalDateInput.sendKeys(disposalDate);
  }

  async getDisposalDateInput() {
    return await this.disposalDateInput.getAttribute('value');
  }

  async setAssetCategoryIdInput(assetCategoryId) {
    await this.assetCategoryIdInput.sendKeys(assetCategoryId);
  }

  async getAssetCategoryIdInput() {
    return await this.assetCategoryIdInput.getAttribute('value');
  }

  async setAssetItemIdInput(assetItemId) {
    await this.assetItemIdInput.sendKeys(assetItemId);
  }

  async getAssetItemIdInput() {
    return await this.assetItemIdInput.getAttribute('value');
  }

  async setDisposalProceedsInput(disposalProceeds) {
    await this.disposalProceedsInput.sendKeys(disposalProceeds);
  }

  async getDisposalProceedsInput() {
    return await this.disposalProceedsInput.getAttribute('value');
  }

  async setNetBookValueInput(netBookValue) {
    await this.netBookValueInput.sendKeys(netBookValue);
  }

  async getNetBookValueInput() {
    return await this.netBookValueInput.getAttribute('value');
  }

  async setProfitOnDisposalInput(profitOnDisposal) {
    await this.profitOnDisposalInput.sendKeys(profitOnDisposal);
  }

  async getProfitOnDisposalInput() {
    return await this.profitOnDisposalInput.getAttribute('value');
  }

  async setScannedDocumentIdInput(scannedDocumentId) {
    await this.scannedDocumentIdInput.sendKeys(scannedDocumentId);
  }

  async getScannedDocumentIdInput() {
    return await this.scannedDocumentIdInput.getAttribute('value');
  }

  async setAssetDealerIdInput(assetDealerId) {
    await this.assetDealerIdInput.sendKeys(assetDealerId);
  }

  async getAssetDealerIdInput() {
    return await this.assetDealerIdInput.getAttribute('value');
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

export class AssetDisposalDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-assetDisposal-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-assetDisposal'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
