import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class EmployeeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-employee div table .btn-danger'));
  title = element.all(by.css('gha-employee div h2#page-heading span')).first();

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

export class EmployeeUpdatePage {
  pageTitle = element(by.id('gha-employee-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  employeeNameInput = element(by.id('field_employeeName'));
  serviceOutletCodeInput = element(by.id('field_serviceOutletCode'));
  employeeRoleInput = element(by.id('field_employeeRole'));
  employeeStaffCodeInput = element(by.id('field_employeeStaffCode'));
  employeeSignatureInput = element(by.id('file_employeeSignature'));
  employeeEmailInput = element(by.id('field_employeeEmail'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setEmployeeNameInput(employeeName) {
    await this.employeeNameInput.sendKeys(employeeName);
  }

  async getEmployeeNameInput() {
    return await this.employeeNameInput.getAttribute('value');
  }

  async setServiceOutletCodeInput(serviceOutletCode) {
    await this.serviceOutletCodeInput.sendKeys(serviceOutletCode);
  }

  async getServiceOutletCodeInput() {
    return await this.serviceOutletCodeInput.getAttribute('value');
  }

  async setEmployeeRoleInput(employeeRole) {
    await this.employeeRoleInput.sendKeys(employeeRole);
  }

  async getEmployeeRoleInput() {
    return await this.employeeRoleInput.getAttribute('value');
  }

  async setEmployeeStaffCodeInput(employeeStaffCode) {
    await this.employeeStaffCodeInput.sendKeys(employeeStaffCode);
  }

  async getEmployeeStaffCodeInput() {
    return await this.employeeStaffCodeInput.getAttribute('value');
  }

  async setEmployeeSignatureInput(employeeSignature) {
    await this.employeeSignatureInput.sendKeys(employeeSignature);
  }

  async getEmployeeSignatureInput() {
    return await this.employeeSignatureInput.getAttribute('value');
  }

  async setEmployeeEmailInput(employeeEmail) {
    await this.employeeEmailInput.sendKeys(employeeEmail);
  }

  async getEmployeeEmailInput() {
    return await this.employeeEmailInput.getAttribute('value');
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

export class EmployeeDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-employee-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-employee'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
