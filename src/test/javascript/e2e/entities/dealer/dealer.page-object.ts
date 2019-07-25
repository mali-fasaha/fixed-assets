import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class DealerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-dealer div table .btn-danger'));
  title = element.all(by.css('gha-dealer div h2#page-heading span')).first();

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

export class DealerUpdatePage {
  pageTitle = element(by.id('gha-dealer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  titleSelect = element(by.id('field_title'));
  dealerNameInput = element(by.id('field_dealerName'));
  dealerAddressInput = element(by.id('field_dealerAddress'));
  dealerPhoneNumberInput = element(by.id('field_dealerPhoneNumber'));
  dealerEmailInput = element(by.id('field_dealerEmail'));
  bankNameInput = element(by.id('field_bankName'));
  bankAccountNumberInput = element(by.id('field_bankAccountNumber'));
  bankBranchInput = element(by.id('field_bankBranch'));
  bankSwiftCodeInput = element(by.id('field_bankSwiftCode'));
  bankPhysicalAddressInput = element(by.id('field_bankPhysicalAddress'));
  domicileInput = element(by.id('field_domicile'));
  taxAuthorityRefInput = element(by.id('field_taxAuthorityRef'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setTitleSelect(title) {
    await this.titleSelect.sendKeys(title);
  }

  async getTitleSelect() {
    return await this.titleSelect.element(by.css('option:checked')).getText();
  }

  async titleSelectLastOption(timeout?: number) {
    await this.titleSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setDealerNameInput(dealerName) {
    await this.dealerNameInput.sendKeys(dealerName);
  }

  async getDealerNameInput() {
    return await this.dealerNameInput.getAttribute('value');
  }

  async setDealerAddressInput(dealerAddress) {
    await this.dealerAddressInput.sendKeys(dealerAddress);
  }

  async getDealerAddressInput() {
    return await this.dealerAddressInput.getAttribute('value');
  }

  async setDealerPhoneNumberInput(dealerPhoneNumber) {
    await this.dealerPhoneNumberInput.sendKeys(dealerPhoneNumber);
  }

  async getDealerPhoneNumberInput() {
    return await this.dealerPhoneNumberInput.getAttribute('value');
  }

  async setDealerEmailInput(dealerEmail) {
    await this.dealerEmailInput.sendKeys(dealerEmail);
  }

  async getDealerEmailInput() {
    return await this.dealerEmailInput.getAttribute('value');
  }

  async setBankNameInput(bankName) {
    await this.bankNameInput.sendKeys(bankName);
  }

  async getBankNameInput() {
    return await this.bankNameInput.getAttribute('value');
  }

  async setBankAccountNumberInput(bankAccountNumber) {
    await this.bankAccountNumberInput.sendKeys(bankAccountNumber);
  }

  async getBankAccountNumberInput() {
    return await this.bankAccountNumberInput.getAttribute('value');
  }

  async setBankBranchInput(bankBranch) {
    await this.bankBranchInput.sendKeys(bankBranch);
  }

  async getBankBranchInput() {
    return await this.bankBranchInput.getAttribute('value');
  }

  async setBankSwiftCodeInput(bankSwiftCode) {
    await this.bankSwiftCodeInput.sendKeys(bankSwiftCode);
  }

  async getBankSwiftCodeInput() {
    return await this.bankSwiftCodeInput.getAttribute('value');
  }

  async setBankPhysicalAddressInput(bankPhysicalAddress) {
    await this.bankPhysicalAddressInput.sendKeys(bankPhysicalAddress);
  }

  async getBankPhysicalAddressInput() {
    return await this.bankPhysicalAddressInput.getAttribute('value');
  }

  async setDomicileInput(domicile) {
    await this.domicileInput.sendKeys(domicile);
  }

  async getDomicileInput() {
    return await this.domicileInput.getAttribute('value');
  }

  async setTaxAuthorityRefInput(taxAuthorityRef) {
    await this.taxAuthorityRefInput.sendKeys(taxAuthorityRef);
  }

  async getTaxAuthorityRefInput() {
    return await this.taxAuthorityRefInput.getAttribute('value');
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

export class DealerDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-dealer-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-dealer'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
