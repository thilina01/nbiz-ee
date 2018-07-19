/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { QuotationItemUpdateComponent } from 'app/entities/quotation-item/quotation-item-update.component';
import { QuotationItemService } from 'app/entities/quotation-item/quotation-item.service';
import { QuotationItem } from 'app/shared/model/quotation-item.model';

describe('Component Tests', () => {
    describe('QuotationItem Management Update Component', () => {
        let comp: QuotationItemUpdateComponent;
        let fixture: ComponentFixture<QuotationItemUpdateComponent>;
        let service: QuotationItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [QuotationItemUpdateComponent]
            })
                .overrideTemplate(QuotationItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuotationItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuotationItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new QuotationItem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.quotationItem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new QuotationItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.quotationItem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
