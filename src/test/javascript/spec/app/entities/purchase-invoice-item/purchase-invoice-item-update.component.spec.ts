/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { PurchaseInvoiceItemUpdateComponent } from 'app/entities/purchase-invoice-item/purchase-invoice-item-update.component';
import { PurchaseInvoiceItemService } from 'app/entities/purchase-invoice-item/purchase-invoice-item.service';
import { PurchaseInvoiceItem } from 'app/shared/model/purchase-invoice-item.model';

describe('Component Tests', () => {
    describe('PurchaseInvoiceItem Management Update Component', () => {
        let comp: PurchaseInvoiceItemUpdateComponent;
        let fixture: ComponentFixture<PurchaseInvoiceItemUpdateComponent>;
        let service: PurchaseInvoiceItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [PurchaseInvoiceItemUpdateComponent]
            })
                .overrideTemplate(PurchaseInvoiceItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PurchaseInvoiceItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseInvoiceItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PurchaseInvoiceItem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.purchaseInvoiceItem = entity;
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
                    const entity = new PurchaseInvoiceItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.purchaseInvoiceItem = entity;
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
