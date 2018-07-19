/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { PurchaseInvoiceItemDetailComponent } from 'app/entities/purchase-invoice-item/purchase-invoice-item-detail.component';
import { PurchaseInvoiceItem } from 'app/shared/model/purchase-invoice-item.model';

describe('Component Tests', () => {
    describe('PurchaseInvoiceItem Management Detail Component', () => {
        let comp: PurchaseInvoiceItemDetailComponent;
        let fixture: ComponentFixture<PurchaseInvoiceItemDetailComponent>;
        const route = ({ data: of({ purchaseInvoiceItem: new PurchaseInvoiceItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [PurchaseInvoiceItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PurchaseInvoiceItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PurchaseInvoiceItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.purchaseInvoiceItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
