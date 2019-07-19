/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { FixedAssetCategoryUpdateComponent } from 'app/entities/fixed-asset-category/fixed-asset-category-update.component';
import { FixedAssetCategoryService } from 'app/entities/fixed-asset-category/fixed-asset-category.service';
import { FixedAssetCategory } from 'app/shared/model/fixed-asset-category.model';

describe('Component Tests', () => {
  describe('FixedAssetCategory Management Update Component', () => {
    let comp: FixedAssetCategoryUpdateComponent;
    let fixture: ComponentFixture<FixedAssetCategoryUpdateComponent>;
    let service: FixedAssetCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FixedAssetCategoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FixedAssetCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FixedAssetCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FixedAssetCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FixedAssetCategory(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new FixedAssetCategory();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
