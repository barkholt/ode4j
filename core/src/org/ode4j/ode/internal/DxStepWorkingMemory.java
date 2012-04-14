/*
 * Created on Apr 14, 2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.ode4j.ode.internal;

public class DxStepWorkingMemory {
    //public:
    public DxStepWorkingMemory() {
        //: 
        m_uiRefCount = 1;//(1), 
        m_ppcProcessingContext = null; 
        m_priReserveInfo = null;//(NULL), 
        m_pmmMemoryManager = null;//(NULL) {}
    }

    //private:
    //private /*friend*/ struct dBase; // To avoid GCC warning regarding private destructor
    private void DESTRUCTOR()//~dxStepWorkingMemory() // Use Release() instead
    {
        //          delete m_ppcProcessingContext;
        //          delete m_priReserveInfo;
        //          delete m_pmmMemoryManager;
    }

    //  public:
    public void Addref()
    {
        Common.dIASSERT(~m_uiRefCount != 0);
        ++m_uiRefCount;
    }

    public void Release()
    {
        Common.dIASSERT(m_uiRefCount != 0);
        if (--m_uiRefCount == 0)
        {
            //delete
            this.DESTRUCTOR();
        }
    }

    //public:
    public void CleanupMemory()
    {
        //delete 
        m_ppcProcessingContext.DESTRUCTOR();
        m_ppcProcessingContext = null;
    }

    //public: 
    public DxWorldProcessContext SureGetWorldProcessingContext() { 
        //return DxWorld.AllocateOnDemand(m_ppcProcessingContext);
        if (m_ppcProcessingContext == null) {
            m_ppcProcessingContext = new DxWorldProcessContext();
        }
        return m_ppcProcessingContext;
    }
    public DxWorldProcessContext GetWorldProcessingContext() { 
        return m_ppcProcessingContext; 
    }

    public DxWorldProcessMemoryReserveInfo GetMemoryReserveInfo() { 
        return m_priReserveInfo; 
    }
    public DxWorldProcessMemoryReserveInfo SureGetMemoryReserveInfo() { 
        return m_priReserveInfo!=null ? m_priReserveInfo : DxUtil.g_WorldProcessDefaultReserveInfo; 
    }
    public void SetMemoryReserveInfo(double fReserveFactor, int uiReserveMinimum)
    {
        if (m_priReserveInfo!=null) { 
            m_priReserveInfo.Assign(fReserveFactor, uiReserveMinimum); 
        }
        else { 
            m_priReserveInfo = new DxWorldProcessMemoryReserveInfo(fReserveFactor, uiReserveMinimum); 
        }
    }
    public void ResetMemoryReserveInfoToDefault()
    {
        if (m_priReserveInfo!=null) { 
            //delete 
            m_priReserveInfo.DESTRUCTOR(); 
            m_priReserveInfo = null; 
        }
    }

    public DxWorldProcessMemoryManager GetMemoryManager() { 
        return m_pmmMemoryManager; 
    }
    public DxWorldProcessMemoryManager SureGetMemoryManager() { 
        return m_pmmMemoryManager!=null ? m_pmmMemoryManager : DxUtil.g_WorldProcessMallocMemoryManager; 
    }
    public void SetMemoryManager(
            DxUtil.alloc_block_fn_t fnAlloc, 
            DxUtil.shrink_block_fn_t fnShrink, 
            DxUtil.free_block_fn_t fnFree) 
    {
        if (m_pmmMemoryManager!=null) { 
            m_pmmMemoryManager.Assign(fnAlloc, fnShrink, fnFree); 
        }
        else { 
            m_pmmMemoryManager = new DxWorldProcessMemoryManager(fnAlloc, fnShrink, fnFree); 
        }
    }
    public void ResetMemoryManagerToDefault()
    {
        if (m_pmmMemoryManager!=null) { 
            //delete 
            m_pmmMemoryManager.DESTRUCTOR(); 
            m_pmmMemoryManager = null; 
        }
    }

    //private:
        private int m_uiRefCount;
    private DxWorldProcessContext m_ppcProcessingContext;
    private DxWorldProcessMemoryReserveInfo m_priReserveInfo;
    private DxWorldProcessMemoryManager m_pmmMemoryManager;

}
