import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ScannedDocumentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-scanned-document div table .btn-danger'));
  title = element.all(by.css('gha-scanned-document div h2#page-heading span')).first();

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

export class ScannedDocumentUpdatePage {
  pageTitle = element(by.id('gha-scanned-document-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descriptionInput = element(by.id('field_description'));
  approvalDocumentInput = element(by.id('file_approvalDocument'));
  invoiceDocumentInput = element(by.id('file_invoiceDocument'));
  lpoDocumentInput = element(by.id('file_lpoDocument'));
  requisitionDocumentInput = element(by.id('file_requisitionDocument'));
  otherDocumentsInput = element(by.id('file_otherDocuments'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setApprovalDocumentInput(approvalDocument) {
    await this.approvalDocumentInput.sendKeys(approvalDocument);
  }

  async getApprovalDocumentInput() {
    return await this.approvalDocumentInput.getAttribute('value');
  }

  async setInvoiceDocumentInput(invoiceDocument) {
    await this.invoiceDocumentInput.sendKeys(invoiceDocument);
  }

  async getInvoiceDocumentInput() {
    return await this.invoiceDocumentInput.getAttribute('value');
  }

  async setLpoDocumentInput(lpoDocument) {
    await this.lpoDocumentInput.sendKeys(lpoDocument);
  }

  async getLpoDocumentInput() {
    return await this.lpoDocumentInput.getAttribute('value');
  }

  async setRequisitionDocumentInput(requisitionDocument) {
    await this.requisitionDocumentInput.sendKeys(requisitionDocument);
  }

  async getRequisitionDocumentInput() {
    return await this.requisitionDocumentInput.getAttribute('value');
  }

  async setOtherDocumentsInput(otherDocuments) {
    await this.otherDocumentsInput.sendKeys(otherDocuments);
  }

  async getOtherDocumentsInput() {
    return await this.otherDocumentsInput.getAttribute('value');
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

export class ScannedDocumentDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-scannedDocument-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-scannedDocument'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
