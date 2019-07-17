/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  FixedAssetInvoiceComponentsPage,
  FixedAssetInvoiceDeleteDialog,
  FixedAssetInvoiceUpdatePage
} from './fixed-asset-invoice.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('FixedAssetInvoice e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fixedAssetInvoiceUpdatePage: FixedAssetInvoiceUpdatePage;
  let fixedAssetInvoiceComponentsPage: FixedAssetInvoiceComponentsPage;
  let fixedAssetInvoiceDeleteDialog: FixedAssetInvoiceDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FixedAssetInvoices', async () => {
    await navBarPage.goToEntity('fixed-asset-invoice');
    fixedAssetInvoiceComponentsPage = new FixedAssetInvoiceComponentsPage();
    await browser.wait(ec.visibilityOf(fixedAssetInvoiceComponentsPage.title), 5000);
    expect(await fixedAssetInvoiceComponentsPage.getTitle()).to.eq('Fixed Asset Invoices');
  });

  it('should load create FixedAssetInvoice page', async () => {
    await fixedAssetInvoiceComponentsPage.clickOnCreateButton();
    fixedAssetInvoiceUpdatePage = new FixedAssetInvoiceUpdatePage();
    expect(await fixedAssetInvoiceUpdatePage.getPageTitle()).to.eq('Create or edit a Fixed Asset Invoice');
    await fixedAssetInvoiceUpdatePage.cancel();
  });

  it('should create and save FixedAssetInvoices', async () => {
    const nbButtonsBeforeCreate = await fixedAssetInvoiceComponentsPage.countDeleteButtons();

    await fixedAssetInvoiceComponentsPage.clickOnCreateButton();
    await promise.all([
      fixedAssetInvoiceUpdatePage.setInvoiceReferenceInput('invoiceReference'),
      fixedAssetInvoiceUpdatePage.setInvoiceDateInput('2000-12-31'),
      fixedAssetInvoiceUpdatePage.setInvoiceAmountInput('5'),
      fixedAssetInvoiceUpdatePage.setAttachmentsInput(absolutePath)
    ]);
    expect(await fixedAssetInvoiceUpdatePage.getInvoiceReferenceInput()).to.eq(
      'invoiceReference',
      'Expected InvoiceReference value to be equals to invoiceReference'
    );
    expect(await fixedAssetInvoiceUpdatePage.getInvoiceDateInput()).to.eq(
      '2000-12-31',
      'Expected invoiceDate value to be equals to 2000-12-31'
    );
    expect(await fixedAssetInvoiceUpdatePage.getInvoiceAmountInput()).to.eq('5', 'Expected invoiceAmount value to be equals to 5');
    const selectedIsProforma = fixedAssetInvoiceUpdatePage.getIsProformaInput();
    if (await selectedIsProforma.isSelected()) {
      await fixedAssetInvoiceUpdatePage.getIsProformaInput().click();
      expect(await fixedAssetInvoiceUpdatePage.getIsProformaInput().isSelected(), 'Expected isProforma not to be selected').to.be.false;
    } else {
      await fixedAssetInvoiceUpdatePage.getIsProformaInput().click();
      expect(await fixedAssetInvoiceUpdatePage.getIsProformaInput().isSelected(), 'Expected isProforma to be selected').to.be.true;
    }
    const selectedIsCreditNote = fixedAssetInvoiceUpdatePage.getIsCreditNoteInput();
    if (await selectedIsCreditNote.isSelected()) {
      await fixedAssetInvoiceUpdatePage.getIsCreditNoteInput().click();
      expect(await fixedAssetInvoiceUpdatePage.getIsCreditNoteInput().isSelected(), 'Expected isCreditNote not to be selected').to.be.false;
    } else {
      await fixedAssetInvoiceUpdatePage.getIsCreditNoteInput().click();
      expect(await fixedAssetInvoiceUpdatePage.getIsCreditNoteInput().isSelected(), 'Expected isCreditNote to be selected').to.be.true;
    }
    expect(await fixedAssetInvoiceUpdatePage.getAttachmentsInput()).to.endsWith(
      fileNameToUpload,
      'Expected Attachments value to be end with ' + fileNameToUpload
    );
    await fixedAssetInvoiceUpdatePage.save();
    expect(await fixedAssetInvoiceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await fixedAssetInvoiceComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FixedAssetInvoice', async () => {
    const nbButtonsBeforeDelete = await fixedAssetInvoiceComponentsPage.countDeleteButtons();
    await fixedAssetInvoiceComponentsPage.clickOnLastDeleteButton();

    fixedAssetInvoiceDeleteDialog = new FixedAssetInvoiceDeleteDialog();
    expect(await fixedAssetInvoiceDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Fixed Asset Invoice?');
    await fixedAssetInvoiceDeleteDialog.clickOnConfirmButton();

    expect(await fixedAssetInvoiceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
