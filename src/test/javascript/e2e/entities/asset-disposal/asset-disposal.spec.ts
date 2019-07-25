/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AssetDisposalComponentsPage, AssetDisposalDeleteDialog, AssetDisposalUpdatePage } from './asset-disposal.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('AssetDisposal e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let assetDisposalUpdatePage: AssetDisposalUpdatePage;
  let assetDisposalComponentsPage: AssetDisposalComponentsPage;
  let assetDisposalDeleteDialog: AssetDisposalDeleteDialog;
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

  it('should load AssetDisposals', async () => {
    await navBarPage.goToEntity('asset-disposal');
    assetDisposalComponentsPage = new AssetDisposalComponentsPage();
    await browser.wait(ec.visibilityOf(assetDisposalComponentsPage.title), 5000);
    expect(await assetDisposalComponentsPage.getTitle()).to.eq('Asset Disposals');
  });

  it('should load create AssetDisposal page', async () => {
    await assetDisposalComponentsPage.clickOnCreateButton();
    assetDisposalUpdatePage = new AssetDisposalUpdatePage();
    expect(await assetDisposalUpdatePage.getPageTitle()).to.eq('Create or edit a Asset Disposal');
    await assetDisposalUpdatePage.cancel();
  });

  it('should create and save AssetDisposals', async () => {
    const nbButtonsBeforeCreate = await assetDisposalComponentsPage.countDeleteButtons();

    await assetDisposalComponentsPage.clickOnCreateButton();
    await promise.all([
      assetDisposalUpdatePage.setDescriptionInput('description'),
      assetDisposalUpdatePage.setDisposalMonthInput('2000-12-31'),
      assetDisposalUpdatePage.setAssetCategoryIdInput('5'),
      assetDisposalUpdatePage.setAssetItemIdInput('5'),
      assetDisposalUpdatePage.setDisposalProceedsInput('5'),
      assetDisposalUpdatePage.setNetBookValueInput('5'),
      assetDisposalUpdatePage.setProfitOnDisposalInput('5'),
      assetDisposalUpdatePage.setScannedDocumentIdInput('5'),
      assetDisposalUpdatePage.setAssetDealerIdInput('5'),
      assetDisposalUpdatePage.setAssetPictureInput(absolutePath)
    ]);
    expect(await assetDisposalUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await assetDisposalUpdatePage.getDisposalMonthInput()).to.eq(
      '2000-12-31',
      'Expected disposalMonth value to be equals to 2000-12-31'
    );
    expect(await assetDisposalUpdatePage.getAssetCategoryIdInput()).to.eq('5', 'Expected assetCategoryId value to be equals to 5');
    expect(await assetDisposalUpdatePage.getAssetItemIdInput()).to.eq('5', 'Expected assetItemId value to be equals to 5');
    expect(await assetDisposalUpdatePage.getDisposalProceedsInput()).to.eq('5', 'Expected disposalProceeds value to be equals to 5');
    expect(await assetDisposalUpdatePage.getNetBookValueInput()).to.eq('5', 'Expected netBookValue value to be equals to 5');
    expect(await assetDisposalUpdatePage.getProfitOnDisposalInput()).to.eq('5', 'Expected profitOnDisposal value to be equals to 5');
    expect(await assetDisposalUpdatePage.getScannedDocumentIdInput()).to.eq('5', 'Expected scannedDocumentId value to be equals to 5');
    expect(await assetDisposalUpdatePage.getAssetDealerIdInput()).to.eq('5', 'Expected assetDealerId value to be equals to 5');
    expect(await assetDisposalUpdatePage.getAssetPictureInput()).to.endsWith(
      fileNameToUpload,
      'Expected AssetPicture value to be end with ' + fileNameToUpload
    );
    await assetDisposalUpdatePage.save();
    expect(await assetDisposalUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await assetDisposalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last AssetDisposal', async () => {
    const nbButtonsBeforeDelete = await assetDisposalComponentsPage.countDeleteButtons();
    await assetDisposalComponentsPage.clickOnLastDeleteButton();

    assetDisposalDeleteDialog = new AssetDisposalDeleteDialog();
    expect(await assetDisposalDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Asset Disposal?');
    await assetDisposalDeleteDialog.clickOnConfirmButton();

    expect(await assetDisposalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
