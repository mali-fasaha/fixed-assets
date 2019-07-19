import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class FixedAssetCategoryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-fixed-asset-category div table .btn-danger'));
  title = element.all(by.css('gha-fixed-asset-category div h2#page-heading span')).first();

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

export class FixedAssetCategoryUpdatePage {
  pageTitle = element(by.id('gha-fixed-asset-category-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  categoryCodeInput = element(by.id('field_categoryCode'));
  categoryNameInput = element(by.id('field_categoryName'));
  categoryDescriptionInput = element(by.id('field_categoryDescription'));
  depreciationRegimeIdInput = element(by.id('field_depreciationRegimeId'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setCategoryCodeInput(categoryCode) {
    await this.categoryCodeInput.sendKeys(categoryCode);
  }

  async getCategoryCodeInput() {
    return await this.categoryCodeInput.getAttribute('value');
  }

  async setCategoryNameInput(categoryName) {
    await this.categoryNameInput.sendKeys(categoryName);
  }

  async getCategoryNameInput() {
    return await this.categoryNameInput.getAttribute('value');
  }

  async setCategoryDescriptionInput(categoryDescription) {
    await this.categoryDescriptionInput.sendKeys(categoryDescription);
  }

  async getCategoryDescriptionInput() {
    return await this.categoryDescriptionInput.getAttribute('value');
  }

  async setDepreciationRegimeIdInput(depreciationRegimeId) {
    await this.depreciationRegimeIdInput.sendKeys(depreciationRegimeId);
  }

  async getDepreciationRegimeIdInput() {
    return await this.depreciationRegimeIdInput.getAttribute('value');
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

export class FixedAssetCategoryDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-fixedAssetCategory-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-fixedAssetCategory'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
