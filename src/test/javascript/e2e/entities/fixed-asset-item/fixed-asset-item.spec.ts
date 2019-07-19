/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FixedAssetItemComponentsPage, FixedAssetItemDeleteDialog, FixedAssetItemUpdatePage } from './fixed-asset-item.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('FixedAssetItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fixedAssetItemUpdatePage: FixedAssetItemUpdatePage;
  let fixedAssetItemComponentsPage: FixedAssetItemComponentsPage;
  let fixedAssetItemDeleteDialog: FixedAssetItemDeleteDialog;
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

  it('should load FixedAssetItems', async () => {
    await navBarPage.goToEntity('fixed-asset-item');
    fixedAssetItemComponentsPage = new FixedAssetItemComponentsPage();
    await browser.wait(ec.visibilityOf(fixedAssetItemComponentsPage.title), 5000);
    expect(await fixedAssetItemComponentsPage.getTitle()).to.eq('Fixed Asset Items');
  });

  it('should load create FixedAssetItem page', async () => {
    await fixedAssetItemComponentsPage.clickOnCreateButton();
    fixedAssetItemUpdatePage = new FixedAssetItemUpdatePage();
    expect(await fixedAssetItemUpdatePage.getPageTitle()).to.eq('Create or edit a Fixed Asset Item');
    await fixedAssetItemUpdatePage.cancel();
  });

  it('should create and save FixedAssetItems', async () => {
    const nbButtonsBeforeCreate = await fixedAssetItemComponentsPage.countDeleteButtons();

    await fixedAssetItemComponentsPage.clickOnCreateButton();
    await promise.all([
      fixedAssetItemUpdatePage.setServiceOutletCodeInput('serviceOutletCode'),
      fixedAssetItemUpdatePage.setAssetCategoryCodeInput('assetCategoryCode'),
      fixedAssetItemUpdatePage.setAssetCategoryInput('assetCategory'),
      fixedAssetItemUpdatePage.setFixedAssetSerialCodeInput('fixedAssetSerialCode'),
      fixedAssetItemUpdatePage.setFixedAssetDescriptionInput('fixedAssetDescription'),
      fixedAssetItemUpdatePage.setPurchaseDateInput('2000-12-31'),
      fixedAssetItemUpdatePage.setPurchaseCostInput('5'),
      fixedAssetItemUpdatePage.setPurchaseTransactionIdInput('5'),
      fixedAssetItemUpdatePage.setOwnershipDocumentIdInput('5'),
      fixedAssetItemUpdatePage.setAssetPictureInput(absolutePath)
    ]);
    expect(await fixedAssetItemUpdatePage.getServiceOutletCodeInput()).to.eq(
      'serviceOutletCode',
      'Expected ServiceOutletCode value to be equals to serviceOutletCode'
    );
    expect(await fixedAssetItemUpdatePage.getAssetCategoryCodeInput()).to.eq(
      'assetCategoryCode',
      'Expected AssetCategoryCode value to be equals to assetCategoryCode'
    );
    expect(await fixedAssetItemUpdatePage.getAssetCategoryInput()).to.eq(
      'assetCategory',
      'Expected AssetCategory value to be equals to assetCategory'
    );
    expect(await fixedAssetItemUpdatePage.getFixedAssetSerialCodeInput()).to.eq(
      'fixedAssetSerialCode',
      'Expected FixedAssetSerialCode value to be equals to fixedAssetSerialCode'
    );
    expect(await fixedAssetItemUpdatePage.getFixedAssetDescriptionInput()).to.eq(
      'fixedAssetDescription',
      'Expected FixedAssetDescription value to be equals to fixedAssetDescription'
    );
    expect(await fixedAssetItemUpdatePage.getPurchaseDateInput()).to.eq(
      '2000-12-31',
      'Expected purchaseDate value to be equals to 2000-12-31'
    );
    expect(await fixedAssetItemUpdatePage.getPurchaseCostInput()).to.eq('5', 'Expected purchaseCost value to be equals to 5');
    expect(await fixedAssetItemUpdatePage.getPurchaseTransactionIdInput()).to.eq(
      '5',
      'Expected purchaseTransactionId value to be equals to 5'
    );
    expect(await fixedAssetItemUpdatePage.getOwnershipDocumentIdInput()).to.eq('5', 'Expected ownershipDocumentId value to be equals to 5');
    expect(await fixedAssetItemUpdatePage.getAssetPictureInput()).to.endsWith(
      fileNameToUpload,
      'Expected AssetPicture value to be end with ' + fileNameToUpload
    );
    await fixedAssetItemUpdatePage.save();
    expect(await fixedAssetItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await fixedAssetItemComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FixedAssetItem', async () => {
    const nbButtonsBeforeDelete = await fixedAssetItemComponentsPage.countDeleteButtons();
    await fixedAssetItemComponentsPage.clickOnLastDeleteButton();

    fixedAssetItemDeleteDialog = new FixedAssetItemDeleteDialog();
    expect(await fixedAssetItemDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Fixed Asset Item?');
    await fixedAssetItemDeleteDialog.clickOnConfirmButton();

    expect(await fixedAssetItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
