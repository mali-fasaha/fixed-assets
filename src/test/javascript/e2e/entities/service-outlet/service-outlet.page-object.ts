import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ServiceOutletComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-service-outlet div table .btn-danger'));
  title = element.all(by.css('gha-service-outlet div h2#page-heading span')).first();

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

export class ServiceOutletUpdatePage {
  pageTitle = element(by.id('gha-service-outlet-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  serviceOutletCodeInput = element(by.id('field_serviceOutletCode'));
  serviceOutletDesignationInput = element(by.id('field_serviceOutletDesignation'));
  descriptionInput = element(by.id('field_description'));
  locationInput = element(by.id('field_location'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setServiceOutletCodeInput(serviceOutletCode) {
    await this.serviceOutletCodeInput.sendKeys(serviceOutletCode);
  }

  async getServiceOutletCodeInput() {
    return await this.serviceOutletCodeInput.getAttribute('value');
  }

  async setServiceOutletDesignationInput(serviceOutletDesignation) {
    await this.serviceOutletDesignationInput.sendKeys(serviceOutletDesignation);
  }

  async getServiceOutletDesignationInput() {
    return await this.serviceOutletDesignationInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setLocationInput(location) {
    await this.locationInput.sendKeys(location);
  }

  async getLocationInput() {
    return await this.locationInput.getAttribute('value');
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

export class ServiceOutletDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-serviceOutlet-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-serviceOutlet'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
