import { NgModule } from '@angular/core';

import { NBizEeSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [NBizEeSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [NBizEeSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class NBizEeSharedCommonModule {}
