import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AssetDepreciationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-asset-depreciation div table .btn-danger'));
  title = element.all(by.css('gha-asset-depreciation div h2#page-heading span')).first();

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

export class AssetDepreciationUpdatePage {
  pageTitle = element(by.id('gha-asset-depreciation-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descriptionInput = element(by.id('field_description'));
  depreciationAmountInput = element(by.id('field_depreciationAmount'));
  depreciationDateInput = element(by.id('field_depreciationDate'));
  categoryIdInput = element(by.id('field_categoryId'));
  assetItemIdInput = element(by.id('field_assetItemId'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setDepreciationAmountInput(depreciationAmount) {
    await this.depreciationAmountInput.sendKeys(depreciationAmount);
  }

  async getDepreciationAmountInput() {
    return await this.depreciationAmountInput.getAttribute('value');
  }

  async setDepreciationDateInput(depreciationDate) {
    await this.depreciationDateInput.sendKeys(depreciationDate);
  }

  async getDepreciationDateInput() {
    return await this.depreciationDateInput.getAttribute('value');
  }

  async setCategoryIdInput(categoryId) {
    await this.categoryIdInput.sendKeys(categoryId);
  }

  async getCategoryIdInput() {
    return await this.categoryIdInput.getAttribute('value');
  }

  async setAssetItemIdInput(assetItemId) {
    await this.assetItemIdInput.sendKeys(assetItemId);
  }

  async getAssetItemIdInput() {
    return await this.assetItemIdInput.getAttribute('value');
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

export class AssetDepreciationDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-assetDepreciation-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-assetDepreciation'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
