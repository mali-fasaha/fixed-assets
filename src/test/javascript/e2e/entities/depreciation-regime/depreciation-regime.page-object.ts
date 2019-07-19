import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class DepreciationRegimeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-depreciation-regime div table .btn-danger'));
  title = element.all(by.css('gha-depreciation-regime div h2#page-heading span')).first();

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

export class DepreciationRegimeUpdatePage {
  pageTitle = element(by.id('gha-depreciation-regime-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  assetDecayTypeSelect = element(by.id('field_assetDecayType'));
  depreciationRateInput = element(by.id('field_depreciationRate'));
  descriptionInput = element(by.id('field_description'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setAssetDecayTypeSelect(assetDecayType) {
    await this.assetDecayTypeSelect.sendKeys(assetDecayType);
  }

  async getAssetDecayTypeSelect() {
    return await this.assetDecayTypeSelect.element(by.css('option:checked')).getText();
  }

  async assetDecayTypeSelectLastOption(timeout?: number) {
    await this.assetDecayTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setDepreciationRateInput(depreciationRate) {
    await this.depreciationRateInput.sendKeys(depreciationRate);
  }

  async getDepreciationRateInput() {
    return await this.depreciationRateInput.getAttribute('value');
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

export class DepreciationRegimeDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-depreciationRegime-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-depreciationRegime'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
