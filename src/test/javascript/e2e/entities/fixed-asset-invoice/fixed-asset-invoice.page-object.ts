import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class FixedAssetInvoiceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-fixed-asset-invoice div table .btn-danger'));
  title = element.all(by.css('gha-fixed-asset-invoice div h2#page-heading span')).first();

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

export class FixedAssetInvoiceUpdatePage {
  pageTitle = element(by.id('gha-fixed-asset-invoice-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  invoiceReferenceInput = element(by.id('field_invoiceReference'));
  invoiceDateInput = element(by.id('field_invoiceDate'));
  invoiceAmountInput = element(by.id('field_invoiceAmount'));
  isProformaInput = element(by.id('field_isProforma'));
  isCreditNoteInput = element(by.id('field_isCreditNote'));
  attachmentsInput = element(by.id('file_attachments'));
  dealerSelect = element(by.id('field_dealer'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setInvoiceReferenceInput(invoiceReference) {
    await this.invoiceReferenceInput.sendKeys(invoiceReference);
  }

  async getInvoiceReferenceInput() {
    return await this.invoiceReferenceInput.getAttribute('value');
  }

  async setInvoiceDateInput(invoiceDate) {
    await this.invoiceDateInput.sendKeys(invoiceDate);
  }

  async getInvoiceDateInput() {
    return await this.invoiceDateInput.getAttribute('value');
  }

  async setInvoiceAmountInput(invoiceAmount) {
    await this.invoiceAmountInput.sendKeys(invoiceAmount);
  }

  async getInvoiceAmountInput() {
    return await this.invoiceAmountInput.getAttribute('value');
  }

  getIsProformaInput(timeout?: number) {
    return this.isProformaInput;
  }
  getIsCreditNoteInput(timeout?: number) {
    return this.isCreditNoteInput;
  }
  async setAttachmentsInput(attachments) {
    await this.attachmentsInput.sendKeys(attachments);
  }

  async getAttachmentsInput() {
    return await this.attachmentsInput.getAttribute('value');
  }

  async dealerSelectLastOption(timeout?: number) {
    await this.dealerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async dealerSelectOption(option) {
    await this.dealerSelect.sendKeys(option);
  }

  getDealerSelect(): ElementFinder {
    return this.dealerSelect;
  }

  async getDealerSelectedOption() {
    return await this.dealerSelect.element(by.css('option:checked')).getText();
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

export class FixedAssetInvoiceDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-fixedAssetInvoice-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-fixedAssetInvoice'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
