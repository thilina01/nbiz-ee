/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { PurchaseInvoiceUpdateComponent } from 'app/entities/purchase-invoice/purchase-invoice-update.component';
import { PurchaseInvoiceService } from 'app/entities/purchase-invoice/purchase-invoice.service';
import { PurchaseInvoice } from 'app/shared/model/purchase-invoice.model';

describe('Component Tests', () => {
    describe('PurchaseInvoice Management Update Component', () => {
        let comp: PurchaseInvoiceUpdateComponent;
        let fixture: ComponentFixture<PurchaseInvoiceUpdateComponent>;
        let service: PurchaseInvoiceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [PurchaseInvoiceUpdateComponent]
            })
                .overrideTemplate(PurchaseInvoiceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PurchaseInvoiceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseInvoiceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PurchaseInvoice(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.purchaseInvoice = entity;
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
                    const entity = new PurchaseInvoice();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.purchaseInvoice = entity;
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
